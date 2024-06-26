package kitchenpos.products.tobe;

import kitchenpos.infra.PurgomalumClient;

public record Name(
        String value
) {
    public Name {
        if (value == null) {
            throw new IllegalArgumentException("name은 필수 입력값입니다.");
        }
    }

    public Name(String value, PurgomalumClient purgomalumClient) {
        this(value);
        if (purgomalumClient.containsProfanity(value)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
        }
    }

}
