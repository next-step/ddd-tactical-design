package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.menu.domain.repository.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class DefaultMenuPolicy implements MenuPolicy {
    private final MenuRepository menuRepository;

    public DefaultMenuPolicy(MenuRepository menuRepository) {
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

        validateMenuPrice(menu.getMenuProducts(), price);
        menu.updatePrice(price);
        return MenuVo.MenuInfo.fromEntity(menu);
    }

    @Override
    public MenuInfo display(UUID menuId) {
        Menu menu = getMenu(menuId);

        validateMenuPrice(menu.getMenuProducts(), menu.getPrice());
        menu.updateDisplayed(true);
        return MenuVo.MenuInfo.fromEntity(menu);
    }

    @Override
    public void validateMenuPrice(MenuPrice price, List<MenuProduct> menuProducts) {
        validateMenuPrice(menuProducts, price);
    }


    private void changeMenuDisplay(Menu menu) {
        BigDecimal totalMenuProductPrice = calculateTotalMenuProductPrice(menu.getMenuProducts());

        boolean shouldBeDisplayed = menu.getPrice().price().compareTo(totalMenuProductPrice) <= 0;
        menu.setDisplayed(shouldBeDisplayed);
    }

    private BigDecimal calculateTotalMenuProductPrice(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
            .map(menuProduct -> menuProduct.getProduct()
                .getPrice()
                .price()
                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            )
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
    }

    private void validateMenuPrice(List<MenuProduct> menuProducts, MenuPrice newPrice) {
        BigDecimal total = calculateTotalMenuProductPrice(menuProducts);
        if (newPrice.price().compareTo(total) > 0) {
            throw new IllegalArgumentException(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString());
        }
    }
}
