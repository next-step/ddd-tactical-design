package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.global.marker.ValueObject;
import kitchenpos.menus.tobe.domain.exception.IllegalMenuGroupNameException;
import kitchenpos.products.tobe.exception.IllegalProductNameException;

import java.util.Objects;

public final class MenuGroupName implements ValueObject {

    private final String name;

    public MenuGroupName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalMenuGroupNameException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
