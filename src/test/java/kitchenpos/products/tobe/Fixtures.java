package kitchenpos.products.tobe;
import kitchenpos.products.tobe.infra.ProductEntity;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;
    public static final Long NONAME_CHICKEN_ID = 3L;
    public static final Long NOPRICE_CHICKEN_ID = 4L;

    public static ProductEntity friedChicken() {
        return new ProductEntity.Builder()
            .id(FRIED_CHICKEN_ID)
            .name("후라이드")
            .price(BigDecimal.valueOf(16_000))
            .build();
    }

    public static ProductEntity seasonedChicken() {
        return new ProductEntity.Builder()
            .id(SEASONED_CHICKEN_ID)
            .name("양념치킨")
            .price(BigDecimal.valueOf(17_000L))
            .build();
    }

    public static ProductEntity nonameProduct (){
        return new ProductEntity.Builder()
            .id(NONAME_CHICKEN_ID)
            .name(null)
            .price(BigDecimal.valueOf(18_000L))
            .build();
    }

    public static ProductEntity noPriceProduct (){
        return new ProductEntity.Builder()
            .id(NOPRICE_CHICKEN_ID)
            .name("간장치킨")
            .price(null)
            .build();
    }
}
