package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class NameValidator {

    private final String name;

    public NameValidator(String name, PurgomalumClient purgomalumClient) {
        validateProductName(name, purgomalumClient);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateProductName(final String productName, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(productName)) {
            throw new IllegalArgumentException("상품 이름 값이 존재하지 않습니다.");
        }
        if (purgomalumClient.containsProfanity(productName)) {
            throw new IllegalArgumentException("상품 이름에 비속어가 존재합니다.");
        }
    }

}
