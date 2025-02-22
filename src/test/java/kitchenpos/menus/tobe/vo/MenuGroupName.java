package kitchenpos.menus.tobe.vo;

import kitchenpos.menus.tobe.exception.InvalidMenuGroupNameException;

import java.util.Objects;

public class MenuGroupName {

    private String value;

    public MenuGroupName(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new InvalidMenuGroupNameException();
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
