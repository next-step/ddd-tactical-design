package kitchenpos.products;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import kitchenpos.products.model.ProductRequest;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static ProductRequest friedChickenRequest() {
        final ProductRequest product = new ProductRequest();
        product.setName("후라이드");
        product.setPrice(BigDecimal.valueOf(16_000L));
        return product;
    }

    public static Product friedChicken() {
        return newProduct("후라이드", FRIED_CHICKEN_ID);
    }

    public static ProductRequest seasonedChickenRequest() {
        final ProductRequest product = new ProductRequest();
        product.setName("양념치킨");
        product.setPrice(BigDecimal.valueOf(16_000L));
        return product;
    }

    public static Product seasonedChicken() {
        return newProduct("양념치킨", SEASONED_CHICKEN_ID);
    }

    private static Product newProduct(String 양념치킨, Long seasonedChickenId) {
        Product product = new Product(양념치킨, Price.of(BigDecimal.valueOf(16_000L)));
        try {
            Field id = Product.class.getDeclaredField("id");
            id.setAccessible(true);
            id.set(product, seasonedChickenId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return product;
    }

}
