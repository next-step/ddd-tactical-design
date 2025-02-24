package kitchenpos.menu.domain.model;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_CATEGORY_NAME_CREATION_EXCEPTION;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private final String value;

    @JsonCreator
    protected MenuGroupName(String value) {
        validateMenuGroupName(value);
        this.value = value;
    }

    protected MenuGroupName() {
        this.value = null;
    }

    private void validateMenuGroupName(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(MENU_CATEGORY_NAME_CREATION_EXCEPTION.getMessage());
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
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
