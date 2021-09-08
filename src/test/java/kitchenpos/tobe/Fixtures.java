package kitchenpos.tobe;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.service.ProductDomainService;
import kitchenpos.tobe.products.application.FakePurgomalumClient;

public class Fixtures {
    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        ProductDomainService productDomainService = new ProductDomainService(new FakePurgomalumClient());
        return new Product(name, BigDecimal.valueOf(price), productDomainService);
    }
}
