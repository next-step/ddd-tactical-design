package kitchenpos.products.tobe.domain.support;

import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductIdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDBasedProductIdGenerator implements ProductIdGenerator {
    @Override
    public ProductId generateId() {
        return ProductId.of(UUID.randomUUID().toString());
    }
}
