package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    private static final String NAME_CREATION_EXCEPTION = "메뉴 이름을 채워주세요!";

    @Column(name = "name", nullable = false)
    private final String value;

    protected MenuName(String value) {
        validateName(value);
        this.value = value;
    }

    protected MenuName() {
        this.value = null;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(NAME_CREATION_EXCEPTION);
        }
    }
}
