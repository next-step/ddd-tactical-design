package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Function;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.exception.MenuPriceInvalidException;
import kitchenpos.menu.domain.exception.MenuStateInvalidException;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.menu.domain.repository.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class DefaultMenuPolicy implements MenuPolicy {
    private final ProductContextService productContextService;
    private final MenuRepository menuRepository;

    public DefaultMenuPolicy(
        final ProductContextService productContextService,
        final MenuRepository menuRepository
    ) {
        this.productContextService = productContextService;
        this.menuRepository = menuRepository;
    }

    /**
     * product -> menu (listener)
     * @param productId
     */
    @Override
    public void hideMenu(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(this::changeMenuDisplay);
    }

    @Override
    public MenuInfo changePrice(UUID menuId, MenuPrice price) {
        Menu menu = getMenu(menuId);

        diffMenuAndTotalMenuProductPrice(
            menu.getMenuProducts(), price,
            total -> new MenuPriceInvalidException());
        menu.updatePrice(price);
        return MenuVo.MenuInfo.fromEntity(menu);
    }

    @Override
    public MenuInfo display(UUID menuId) {
        Menu menu = getMenu(menuId);

        diffMenuAndTotalMenuProductPrice(
            menu.getMenuProducts(), menu.getPrice(),
            total -> new MenuStateInvalidException());
        menu.updateDisplayed(true);
        return MenuVo.MenuInfo.fromEntity(menu);
    }

    @Override
    public void validateMenuPrice(MenuPrice price, List<MenuProduct> menuProducts) {
        diffMenuAndTotalMenuProductPrice(
            menuProducts, price,
            total -> new MenuPriceInvalidException());
    }


    private void changeMenuDisplay(Menu menu) {
        BigDecimal totalMenuProductPrice = calculateTotalMenuProductPrice(menu.getMenuProducts());

        boolean shouldBeDisplayed = menu.isPriceLessThanOrEqual(totalMenuProductPrice);
        menu.updateDisplayed(shouldBeDisplayed);
    }

    private BigDecimal calculateTotalMenuProductPrice(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
            .map(menuProduct -> productContextService.getTotalPrice(menuProduct.getProductId(), BigDecimal.valueOf(menuProduct.getQuantity().quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
    }

    private void diffMenuAndTotalMenuProductPrice(
        List<MenuProduct> menuProducts,
        MenuPrice newPrice,
        Function<BigDecimal, RuntimeException> exceptionFunction
    ) {
        BigDecimal total = calculateTotalMenuProductPrice(menuProducts);
        if (newPrice.isGreaterThan(total)) {
            throw exceptionFunction.apply(total);
        }
    }
}
