package kitchenpos.products.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuExceptionMessage.MENU_PRICE_MORE_PRODUCTS_SUM;

@Service
public class MenuProductPriceHandler {
    public void hideMenuDisplayMenuPriceGreaterThanSum(Map<UUID, Product> productMap, List<Menu> menus) {
        for (final Menu menu : menus) {
            BigDecimal sum = sumMenuProductPrice(productMap, menu.getMenuProductList());
            hideMenuDisplayMenuPriceGreaterThanSum(menu.getPriceValue(), sum, menu);
        }
    }

    public void checkPrice(Map<UUID, Product> productMap, Price price, List<MenuProduct> menuProducts) {
        Price sum = Price.of(sumMenuProductPrice(productMap, menuProducts));
        if (price.isGreaterThan(sum)) {
            throw new IllegalArgumentException(MENU_PRICE_MORE_PRODUCTS_SUM);
        }
    }

    public BigDecimal sumMenuProductPrice(Map<UUID, Product> productMap, List<MenuProduct> menuProductList) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProductList) {
            Product product = productMap.get(menuProduct.getProductId());
            sum = sum.add(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        return sum;
    }

    private void hideMenuDisplayMenuPriceGreaterThanSum(BigDecimal price, BigDecimal sum, Menu menu) {
        if (price.compareTo(sum) > 0) {
            menu.notDisplayed();
        }
    }

}
