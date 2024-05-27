package kitchenpos;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductNameValidationService;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class ToBeFixtures {

    public Product 후라이드_20000 = new Product(
            UUID.randomUUID(),
            "후라이드",
            BigDecimal.valueOf(20_000),
            new ProductNameValidationService(new FakePurgomalumClient())
    );

    public static Product productOf(String name, BigDecimal price, ProductNameValidationService productNameValidationService) {
        Product product = new Product(
                UUID.randomUUID(), name, price, productNameValidationService
        );
        return product;
    }

    public static MenuProduct menuProductOf(Product product, long quantity) {
        MenuProduct menuProduct = new MenuProduct(
                new Random().nextLong(), product, quantity
        );
        return menuProduct;
    }
}
