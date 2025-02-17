package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {
    private static final String MENU_CATEGORY_NAME_CREATION_EXCEPTION = "메뉴 카테고리 이름을 채워주세요!";

    @Column(name = "name", nullable = false)
    private final String value;

    public MenuGroupName(String value) {
        validateMenuGroupName(value);
        this.value = value;
    }

    private void validateMenuGroupName(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(MENU_CATEGORY_NAME_CREATION_EXCEPTION);
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
