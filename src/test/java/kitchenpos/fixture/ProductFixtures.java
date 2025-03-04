package kitchenpos.fixture;

import kitchenpos.core.products.application.dto.CreateProductRequest;
import kitchenpos.core.products.tobe.domain.*;
import kitchenpos.core.products.tobe.domain.support.DefaultProductNamePolicy;
import kitchenpos.core.shared.domain.ProfanityChecker;
import kitchenpos.core.shared.identifier.ProductId;
import kitchenpos.core.shared.value.Money;
import kitchenpos.core.products.application.FakeProfanityChecker;

import java.util.UUID;

public class ProductFixtures {

    private static ProductIdGenerator productIdGenerator = () -> ProductId.of(UUID.randomUUID());
    private static ProfanityChecker profanityChecker = new FakeProfanityChecker();
    private static ProductNamePolicy productNamePolicy = new DefaultProductNamePolicy(profanityChecker);

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        final Product product = Product.create(
                productIdGenerator.generateId(),
                ProductName.create(productNamePolicy, name),
                ProductPrice.of(Money.wons(price))
        );
        return product;
    }

    public static CreateProductRequest createProductRequest(final String name, final long price) {
        return new CreateProductRequest(
                productIdGenerator.generateId(),
                ProductName.create(productNamePolicy, name),
                ProductPrice.of(Money.wons(price)));
    }

}
