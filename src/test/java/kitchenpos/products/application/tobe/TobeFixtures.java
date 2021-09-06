package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.ui.ProductForm;

import java.math.BigDecimal;
import java.util.UUID;

public class TobeFixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static TobeProduct product() {
        return product("후라이드", 16_000L);
    }

    public static TobeProduct product(final String name, final long price) {
        return TobeProduct.of(productForm(name, price));
    }

    public static ProductForm productForm(final String name, final long price) {
        final ProductForm productForm = new ProductForm();
        productForm.setName(name);
        productForm.setPrice(BigDecimal.valueOf(price));
        return productForm;
    }
}
