package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuException;

@Embeddable
public class MenuGroupName {

    String name;

    protected MenuGroupName() {
    }

    public MenuGroupName(String name) {
        if (isNullOrEmpty(name)) {
            throw new MenuException(MenuErrorCode.NAME_IS_NOT_EMPTY_OR_NULL);
        }
        this.name = name;
    }

    public boolean isNullOrEmpty(String name) {
        return name == null || name.isEmpty();
    }

    public String getValue() {
        return name;
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
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
