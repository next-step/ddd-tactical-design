package kitchenpos.product.tobe.domain;

import java.util.UUID;

public interface ProductValidator {
    void validate(UUID productId, Long price);
}
