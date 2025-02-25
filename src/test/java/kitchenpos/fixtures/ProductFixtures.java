package kitchenpos.fixtures;

import static java.math.BigDecimal.valueOf;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductFixtures {

    public static final UUID 후라이드치킨_ID = UUID.randomUUID();
    public static final UUID 양념치킨_ID = UUID.randomUUID();

    public static final String 후라이드치킨_이름 = "후라이드치킨";
    public static final String 양념치킨_이름 = "양념치킨";

    public static final BigDecimal 후라이드치킨_가격 = valueOf(16000);
    public static final BigDecimal 양념치킨_가격 = valueOf(17000);

    public static Product 후라이드치킨() {
        Product product = new Product(new ProductName(후라이드치킨_이름), new ProductPrice(후라이드치킨_가격));
        return product;
    }

    public static Product 양념치킨() {
        Product product = new Product(new ProductName(양념치킨_이름), new ProductPrice(양념치킨_가격));
        return product;
    }
    public static Product createProduct(final String name, final BigDecimal price) {
        Product product = new Product(new ProductName(name), new ProductPrice(price));
        return product;
    }

    /**
     * OrderServiceTest
     */
    public static Product product() {
        return product("후라이드치킨", 16_000L);
    }

    public static Product product(final String name, final long price) {
        final Product product = new Product(new ProductName(name), new ProductPrice(BigDecimal.valueOf(price)));
        return product;
    }
}
