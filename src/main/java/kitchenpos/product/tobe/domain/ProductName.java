package kitchenpos.product.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.common.infra.PurgomalumClient;

import java.util.Objects;

@Embeddable
public class ProductName {
    private String name;

    protected ProductName() {
    }

    protected ProductName(String name, PurgomalumClient purgomalumClient) {
        validateName(name, purgomalumClient);
        this.name = name;
    }

    private void validateName(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("상품명이 비어있습니다.");
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다: " + name);
        }
    }

    public String getName() {
        return name;
    }
}
