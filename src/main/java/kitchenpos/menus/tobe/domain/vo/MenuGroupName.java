package kitchenpos.menus.tobe.domain.vo;

import java.util.Objects;

public class MenuGroupName {
    private final String value;

    private MenuGroupName(String value) {
        this.value = value;
    }

    public static MenuGroupName of(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new MenuGroupName(name);
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
