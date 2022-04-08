package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class DisplayedName {
    private static final String INVALID_NAME_MESSAGE = "잘못된 메뉴 이름 입니다.";

    private final String value;

    public DisplayedName(String value, PurgomalumClient purgomalumClient) {
        validate(value, purgomalumClient);
        this.value = value;
    }

    private void validate(String value, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(value) || purgomalumClient.containsProfanity(value)) {
            throw new IllegalArgumentException(INVALID_NAME_MESSAGE);
        }
    }
}
