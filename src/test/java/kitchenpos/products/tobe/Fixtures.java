package kitchenpos.products.tobe;

<<<<<<< HEAD
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;
    public static final Long NONAME_CHICKEN_ID = 3L;
    public static final Long NOPRICE_CHICKEN_ID = 4L;

    public static Product friedChicken() {
        return new Product.Builder()
            .id(FRIED_CHICKEN_ID)
            .name("후라이드")
            .price(BigDecimal.valueOf(16_000))
            .build();
    }

    public static Product seasonedChicken() {
        return new Product.Builder()
            .id(SEASONED_CHICKEN_ID)
            .name("양념치킨")
            .price(BigDecimal.valueOf(16_000L))
            .build();
    }

    public static Product nonameProduct (){
        return new Product.Builder()
            .id(NONAME_CHICKEN_ID)
            .name(null)
            .price(BigDecimal.valueOf(16_000L))
            .build();
    }

    public static Product noPriceProduct (){
        return new Product.Builder()
            .id(NOPRICE_CHICKEN_ID)
            .name("간장치킨")
            .price(null)
            .build();
    }
=======
public class Fixtures {
>>>>>>> refactoring(kitchenpos) : JpaRepository 추가.
}
