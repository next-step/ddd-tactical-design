package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;

public class MenuValidator {
    private MenuValidator() {

    }

    public static void validatePriceNotBiggerThanSumOfProducts(MenuProducts menuProducts, Price price) {
        Price sum = menuProducts.calculateSum();

        if(sum.isLessThan(price)) {
            throw new IllegalArgumentException("menu price cannot bigger than sum of product's sum");
        }
    }
}
