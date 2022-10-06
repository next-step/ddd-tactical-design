package kitchenpos.menus.domain.tobe;

import java.util.Objects;

public class MenuGroupName {
    private final String value;

    public MenuGroupName(final String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("menuGroup name is required");
        }
        this.value = name;
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
        return value != null ? value.hashCode() : 0;
    }
}
