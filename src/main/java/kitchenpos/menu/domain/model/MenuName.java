package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    private static final String MENU_NAME_CREATION_EXCEPTION = "메뉴 이름을 채워주세요!";

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
            throw new IllegalArgumentException(MENU_NAME_CREATION_EXCEPTION);
        }
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
        MenuName menuName = (MenuName) o;
        return Objects.equals(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
