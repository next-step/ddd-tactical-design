package kitchenpos.product.tobe.domain.validate;

import kitchenpos.common.infra.PurgomalumClient;

import java.util.Objects;

public class ProductNameValidator {
    private final PurgomalumClient purgomalumClient;

    public ProductNameValidator(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public void validate(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("상품명이 비어있습니다.");
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다: " + name);
        }
    }
}
