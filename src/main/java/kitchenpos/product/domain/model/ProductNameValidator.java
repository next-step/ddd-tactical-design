package kitchenpos.product.domain.model;

import kitchenpos.global.exception.ErrorCode;
import kitchenpos.product.domain.service.ProductPurgomalumClient;

public record ProductNameValidator(
    String name,
    ProductPurgomalumClient purgomalumClient
) {
    public ProductNameValidator {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NAME_NOT_ALLOWED.toString());
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NAME_PROFANITY_NOT_ALLOWED.toString());
        }
    }
}
