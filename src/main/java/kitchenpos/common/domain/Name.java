package kitchenpos.common.domain;

import jakarta.persistence.Column;
import java.util.Objects;

public class Name {
    private static final String NAME_CREATION_EXCEPTION = "이름을 채워주세요!";

    @Column(name = "name", nullable = false)
    private final String value;

    protected Name(String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(NAME_CREATION_EXCEPTION);
        }
    }

    public String getValue() {
        return value;
    }
}
