package kitchenpos;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class Fixtures2 {
    public static Product chicken() {
        return new Product("치킨", BigDecimal.valueOf(20000), new FakePurgomalumClient());
    }

    public static Product frenchFries() {
        return new Product("감자튀김", BigDecimal.valueOf(5000), new FakePurgomalumClient());
    }

    public static MenuProduct chickenMenuProduct() {
        return chickenMenuProductWithQuantity(1);
    }

    public static MenuProduct chickenMenuProductWithQuantity(int quantity) {
        return new MenuProduct(chicken(), quantity);
    }

    public static MenuProduct frenchFriesMenuProduct() {
        return frenchFriesMenuProductWithQuantity(1);
    }

    public static MenuProduct frenchFriesMenuProductWithQuantity(int quantity) {
        return new MenuProduct(frenchFries(), quantity);
    }
}
