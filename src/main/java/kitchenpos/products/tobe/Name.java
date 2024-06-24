package kitchenpos.products.tobe;

import kitchenpos.infra.PurgomalumClient;

public record Name(
        String value
) {
    public Name {
        if (value == null) {
            throw new IllegalArgumentException("상품명은 필수 입력값입니다.");
        }
    }

    public Name(String value, PurgomalumClient purgomalumClient) {
        this(value);
        purgomalumClient.containsProfanity(value);
    }

}
