package kitchenpos.menu.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import kitchenpos.common.application.PurgomalumClient;
import kitchenpos.menu.domain.model.*;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.domain.service.MarginValidator;
import kitchenpos.menu.domain.service.MenuProductValidator;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
    private static final String NONE_MARGIN_EXCEPTION = "마진이 남지 않습니다! 마진을 남기게 만들어주세요!";

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final MarginValidator marginValidator;
    private final MenuProductValidator menuProductValidator;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient, MarginValidator marginValidator, MenuProductValidator menuProductValidator
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.marginValidator = marginValidator;
        this.menuProductValidator = menuProductValidator;
    }

    @Transactional
    public Menu create(final Menu request) {
        final String name = request.getInnerName();
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        final BigDecimal price = request.getInnerPrice();
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);

        final List<MenuProduct> menuProductRequests = request.getMenuProducts();
        menuProductValidator.validateMenuProduct(menuProductRequests);
        final List<MenuProduct> menuProducts = createMenuProductsByRequest(menuProductRequests);

        final Menu menu = new Menu(UUID.randomUUID(), new MenuName(name), new MenuPrice(price), menuGroup, request.isDisplayed(), menuProducts, menuGroup.getId());
        validateMargin(menu);
        return menuRepository.save(menu);
    }

    private List<MenuProduct> createMenuProductsByRequest(List<MenuProduct> menuProductRequests) {
        return menuProductRequests.stream().map(this::createMenuProductByRequest).toList();
    }

    private MenuProduct createMenuProductByRequest(MenuProduct request) {
        final long quantity = request.getInnerQuantity();
        final Product product = productRepository.findById(request.getProductId())
                .orElseThrow(NoSuchElementException::new);
        return new MenuProduct(product, new MenuProductQuantity(quantity), product.getId());
    }

    private void validateMargin(Menu menu) {
        boolean hasMargin = marginValidator.checkMargin(menu);
        if (!hasMargin) {
            throw new IllegalStateException(NONE_MARGIN_EXCEPTION);
        }
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {
        final BigDecimal price = request.getInnerPrice();
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(price);
        validateMargin(menu);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changeDisplay(true);
        validateMargin(menu);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changeDisplay(false);
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
