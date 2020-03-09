package kitchenpos.products.tobe;

import kitchenpos.products.tobe.domain.Product;

import java.lang.reflect.Field;

public class Fixtures {

    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static Product friedChicken() {
        final Product product = new Product("후라이드", 15000L);
        setId(product, FRIED_CHICKEN_ID);
        return product;
    }

    public static Product seasonedChicken() {
        final Product product = new Product("양념치킨", 16000L);
        setId(product, SEASONED_CHICKEN_ID);
        return product;
    }

    public static void setId(Object object, Long id) {
        try {
            final Field field = object.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(object, id);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
