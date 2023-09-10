package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.exception.DisplayedNameException;
import kitchenpos.menus.exception.MenuErrorCode;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupDisplayedName {
    private String name;

    protected MenuGroupDisplayedName() {

    }

    public MenuGroupDisplayedName(String name) {
        if (isNullAndEmpty(name)) {
            throw new DisplayedNameException(MenuErrorCode.NAME_IS_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private boolean isNullAndEmpty(String name) {
        return name == null || name.isBlank();
    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupDisplayedName that = (MenuGroupDisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
