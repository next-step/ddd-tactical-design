package kitchenpos.product.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.product.domain.service.ProductPurgomalumClient;

@Embeddable
public record ProductName(String name) {

    public static ProductName of(String name, ProductPurgomalumClient purgomalumClient) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NAME_NOT_ALLOWED.toString());
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NAME_PROFANITY_NOT_ALLOWED.toString());
        }
        return new ProductName(name);
    }
}
