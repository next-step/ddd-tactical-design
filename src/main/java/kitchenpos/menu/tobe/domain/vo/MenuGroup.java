package kitchenpos.menu.tobe.domain.vo;

import kitchenpos.common.name.Name;

public class MenuGroup {

    public final Name name;

    public MenuGroup(final Name name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuGroup menuGroup = (MenuGroup) o;

        return name.equals(menuGroup.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
