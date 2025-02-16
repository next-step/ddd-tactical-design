package kitchenpos.common.domain;

import java.util.Objects;

public class Name {
    private static final String NAME_CREATION_EXCEPTION = "이름을 채워주세요!";
    private final String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(NAME_CREATION_EXCEPTION);
        }
    }
}
