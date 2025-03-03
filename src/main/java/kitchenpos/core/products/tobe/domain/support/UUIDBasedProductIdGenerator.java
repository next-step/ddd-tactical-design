package kitchenpos.core.products.tobe.domain.support;

import kitchenpos.core.shared.identifier.ProductId;
import kitchenpos.core.products.tobe.domain.ProductIdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDBasedProductIdGenerator implements ProductIdGenerator {
    @Override
    public ProductId generateId() {
        return ProductId.of(UUID.randomUUID());
    }
}
