package kitchenpos.products.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MenuProductPriceHandler {
    public void hideMenuDisplayMenuPriceGreaterThanSum(Map<UUID, Product> productMap, List<Menu> menus) {
        for (final Menu menu : menus) {
            BigDecimal sum = sumMenuProductPrice(productMap, menu);
            hideMenuDisplayMenuPriceGreaterThanSum(menu.getPrice(), sum, menu);
        }
    }

    public void checkPrice(Map<UUID, Product> productMap, Menu menu) {
        BigDecimal sum = sumMenuProductPrice(productMap, menu);
        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
    }

    public BigDecimal sumMenuProductPrice(Map<UUID, Product> productMap, Menu menu) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
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
            menu.setDisplayed(false);
        }
    }

}
