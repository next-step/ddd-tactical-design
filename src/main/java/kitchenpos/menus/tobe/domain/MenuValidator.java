package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;

import java.util.List;

public class MenuValidator {
    private MenuValidator() {

    }

    public static void validatePriceNotBiggerThanSumOfProducts(List<MenuProduct> menuProducts, Price price) {
        Price sum = menuProducts.stream()
                .map(menuProduct -> menuProduct.getProductPrice().multiply(menuProduct.getQuantity()))
                .reduce(Price.ZERO, Price::add);

        if(sum.isLessThan(price)) {
            throw new IllegalArgumentException("menu price cannot bigger than sum of product's sum");
        }
    }
}
