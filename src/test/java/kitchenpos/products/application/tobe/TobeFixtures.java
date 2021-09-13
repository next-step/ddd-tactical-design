package kitchenpos.products.application.tobe;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.TobeProduct;

import java.math.BigDecimal;
import java.util.UUID;

public class TobeFixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static TobeProduct product() {
        return product("후라이드", 16_000L);
    }

    public static TobeProduct product(final String name, final Long price) {
        ProductName productName = new ProductName(name, new FakePurgomalumClient());
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));
        return new TobeProduct(productName, productPrice);
    }
}
