package kitchenpos.products.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.NewMenu;
import kitchenpos.menus.tobe.domain.NewMenuProduct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuExceptionMessage.MENU_PRICE_MORE_PRODUCTS_SUM;

@Service
public class MenuProductPriceHandler {
    public void hideMenuDisplayMenuPriceGreaterThanSum(Map<UUID, Product> productMap, List<NewMenu> newMenus) {
        for (final NewMenu newMenu : newMenus) {
            BigDecimal sum = sumMenuProductPrice(productMap, newMenu.getMenuProductList());
            hideMenuDisplayMenuPriceGreaterThanSum(newMenu.getPriceValue(), sum, newMenu);
        }
    }

    public void checkPrice(Map<UUID, Product> productMap, Price price, List<NewMenuProduct> newMenuProducts) {
        Price sum = Price.of(sumMenuProductPrice(productMap, newMenuProducts));
        if (price.isGreaterThan(sum)) {
            throw new IllegalArgumentException(MENU_PRICE_MORE_PRODUCTS_SUM);
        }
    }

    public BigDecimal sumMenuProductPrice(Map<UUID, Product> productMap, List<NewMenuProduct> newMenuProductList) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final NewMenuProduct newMenuProduct : newMenuProductList) {
            Product product = productMap.get(newMenuProduct.getProductId());
            sum = sum.add(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(newMenuProduct.getQuantity()))
            );
        }
        return sum;
    }

    private void hideMenuDisplayMenuPriceGreaterThanSum(BigDecimal price, BigDecimal sum, NewMenu newMenu) {
        if (price.compareTo(sum) > 0) {
            newMenu.notDisplayed();
        }
    }

}
