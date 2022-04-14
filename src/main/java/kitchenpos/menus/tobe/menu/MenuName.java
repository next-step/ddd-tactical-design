package kitchenpos.menus.tobe.menu;

import java.util.Objects;

public class MenuName {
    private final String value;

    public MenuName(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("메뉴 이름은 공백일 수 없습니다.");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuName that = (MenuName) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}

