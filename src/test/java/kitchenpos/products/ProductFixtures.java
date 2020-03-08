package kitchenpos.products;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ProductFixtures {
    public static Product friedChicken() {
        return new Product("후라이드", BigDecimal.valueOf(16_000L));
    }

    public static Product seasonedChicken() {
        return new Product("양념치킨", BigDecimal.valueOf(16_000L));
    }

    public static List<Product> chickenSet() {
        return Arrays.asList(friedChicken(), seasonedChicken());
    }

}
