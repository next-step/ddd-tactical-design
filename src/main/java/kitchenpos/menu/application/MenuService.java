package kitchenpos.menu.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import kitchenpos.common.application.PurgomalumClient;
import kitchenpos.menu.domain.model.*;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.domain.service.MarginValidator;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final MarginValidator marginValidator;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient, MarginValidator marginValidator
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.marginValidator = marginValidator;
    }

    @Transactional
    public Menu create(final Menu request) {
        final BigDecimal price = request.getInnerPrice();
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);
        final List<MenuProduct> menuProductRequests = request.getMenuProducts();
        final List<Product> products = productRepository.findAllByIdIn(
                menuProductRequests.stream()
                        .map(MenuProduct::getProductId)
                        .toList()
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<MenuProduct> menuProducts = createMenuProducts(menuProductRequests);
        final String name = request.getInnerName();
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final Menu menu = new Menu(UUID.randomUUID(), new MenuName(name), new MenuPrice(price), menuGroup, request.isDisplayed(), menuProducts, menuGroup.getId());
        validateMargin(menu);
        return menuRepository.save(menu);
    }

    private List<MenuProduct> createMenuProducts(List<MenuProduct> menuProductRequests) {
        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (final MenuProduct menuProductRequest : menuProductRequests) {
            final long quantity = menuProductRequest.getInnerQuantity();
            final Product product = productRepository.findById(menuProductRequest.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            final MenuProduct menuProduct = new MenuProduct(product, new MenuProductQuantity(quantity), product.getId());
            menuProducts.add(menuProduct);
        }
        return menuProducts;
    }

    private void validateMargin(Menu menu) {
        boolean hasMargin = marginValidator.checkMargin(menu);
        if (!hasMargin) {
            throw new IllegalStateException("마진이 남지 않습니다! 마진을 남기게 만들어주세요!");
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
