package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_CATEGORY_NAME_CREATION_EXCEPTION;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private final String value;

    protected MenuGroupName(String value) {
        validateMenuGroupName(value);
        this.value = value;
    }

    private void validateMenuGroupName(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(MENU_CATEGORY_NAME_CREATION_EXCEPTION.getMessage());
        }
    }

    protected MenuGroupName() {
        this.value = null;
    }

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
