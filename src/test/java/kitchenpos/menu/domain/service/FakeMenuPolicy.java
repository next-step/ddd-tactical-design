package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Function;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.service.ProductContextService;

public class FakeMenuPolicy implements MenuPolicy {

    private final MenuRepository menuRepository;

    private final ProductContextService productContextService;

    public FakeMenuPolicy(
        final MenuRepository menuRepository,
        final ProductContextService productContextService
    ) {
        this.menuRepository = menuRepository;
        this.productContextService = productContextService;
    }

    @Override
    public void hideMenu(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(menu -> {
            menu.setDisplayed(false);
            menuRepository.save(menu);
        });
    }

    @Override
    public MenuInfo changePrice(UUID menuId, MenuPrice price) {
        var menu = getMenu(menuId);
        diffMenuAndTotalMenuProductPrice(
            menu.getMenuProducts(), price,
            total -> new IllegalArgumentException(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString()));
        return MenuInfo.fromEntity(menu);
    }

    @Override
    public MenuInfo display(UUID menuId) {
        var menu = getMenu(menuId);
        diffMenuAndTotalMenuProductPrice(
            menu.getMenuProducts(), menu.getPrice(),
            total -> new IllegalStateException(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString()));
        return MenuInfo.fromEntity(menu);
    }

    @Override
    public void validateMenuPrice(MenuPrice price, List<MenuProduct> menuProducts) {
        diffMenuAndTotalMenuProductPrice(
            menuProducts, price,
            total -> new IllegalArgumentException(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString()));
    }

    private BigDecimal calculate(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
        .map(menuProduct -> productContextService.getTotalPrice(menuProduct.getProductId(), BigDecimal.valueOf(menuProduct.getQuantity())))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
    }

    private Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
    }

    private void diffMenuAndTotalMenuProductPrice(
        List<MenuProduct> menuProducts,
        MenuPrice price,
        Function<BigDecimal, RuntimeException> exceptionFunction
    ) {
        final BigDecimal menuPrice = price.price();
        final BigDecimal totalMenuProductPrice = calculate(menuProducts);

        if (menuPrice.compareTo(totalMenuProductPrice) > 0) {
            throw exceptionFunction.apply(totalMenuProductPrice);
        }
    }
}

