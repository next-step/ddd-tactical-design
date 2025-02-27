package kitchenpos.menu.application;

import static kitchenpos.menu.exception.MenuExceptionMessage.NONE_MARGIN_EXCEPTION;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menu.application.dto.ChangeMenuPriceServiceRq;
import kitchenpos.menu.application.dto.CreateMenuServiceRq;
import kitchenpos.menu.application.dto.CreateMenuServiceRq.MenuProductServiceRq;
import kitchenpos.menu.application.dto.MenuServiceRs;
import kitchenpos.menu.application.dto.SimpleMenuServiceRs;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuName;
import kitchenpos.menu.domain.model.MenuNameCreationService;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.menu.domain.model.MenuProductQuantity;
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
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final MarginValidator marginValidator;
    private final MenuProductValidator menuProductValidator;
    private final MenuNameCreationService menuNameCreationService;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository,
            MarginValidator marginValidator,
            MenuProductValidator menuProductValidator,
            MenuNameCreationService menuNameCreationService
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.marginValidator = marginValidator;
        this.menuProductValidator = menuProductValidator;
        this.menuNameCreationService = menuNameCreationService;
    }

    @Transactional
    public MenuServiceRs create(final CreateMenuServiceRq request) {
        final BigDecimal price = request.getPrice();
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);

        List<MenuProductServiceRq> menuProductRequests = request.getMenuProductDtos();
        final List<MenuProduct> menuProducts = createMenuProductsByRequest(menuProductRequests);
        menuProductValidator.validateMenuProduct(menuProducts);

        final String name = request.getName();
        MenuName menuName = menuNameCreationService.createName(name);

        final Menu menu = new Menu(menuName, new MenuPrice(price), menuGroup, request.isDisplayed(), menuProducts,
                menuGroup.getId());
        validateMargin(menu);
        menuRepository.save(menu);

        return new MenuServiceRs(menu);
    }

    private List<MenuProduct> createMenuProductsByRequest(List<MenuProductServiceRq> menuProductRequests) {
        return menuProductRequests.stream().map(this::createMenuProductByRequest).toList();
    }

    private MenuProduct createMenuProductByRequest(MenuProductServiceRq request) {
        final long quantity = request.getQuantity();
        final Product product = productRepository.findById(request.getProductId())
                .orElseThrow(NoSuchElementException::new);
        return new MenuProduct(product, new MenuProductQuantity(quantity), product.getId());
    }

    private void validateMargin(Menu menu) {
        boolean hasMargin = marginValidator.checkMargin(menu);
        if (!hasMargin) {
            throw new IllegalStateException(NONE_MARGIN_EXCEPTION.getMessage());
        }
    }

    @Transactional
    public SimpleMenuServiceRs changePrice(final UUID menuId, final ChangeMenuPriceServiceRq request) {
        final BigDecimal price = request.getPrice();
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(price);
        validateMargin(menu);
        return new SimpleMenuServiceRs(menu);
    }

    @Transactional
    public SimpleMenuServiceRs display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        validateMargin(menu);
        menu.changeDisplay(true);
        return new SimpleMenuServiceRs(menu);
    }

    @Transactional
    public SimpleMenuServiceRs hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changeDisplay(false);
        return new SimpleMenuServiceRs(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuServiceRs> findAll() {
        return menuRepository.findAll().stream()
                .map(MenuServiceRs::new)
                .toList();
    }
}
