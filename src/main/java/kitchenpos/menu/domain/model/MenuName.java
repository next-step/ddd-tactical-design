package kitchenpos.menu.domain.model;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_NAME_CREATION_EXCEPTION;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private final String value;

    public MenuName(String value) {
        validateName(value);
        this.value = value;
    }

    protected MenuName() {
        this.value = null;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(MENU_NAME_CREATION_EXCEPTION.getMessage());
        }
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuName menuName = (MenuName) o;
        return Objects.equals(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
