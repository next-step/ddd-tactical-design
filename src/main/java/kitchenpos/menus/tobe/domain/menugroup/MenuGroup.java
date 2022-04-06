package kitchenpos.menus.tobe.domain.menugroup;

import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;

public final class MenuGroup {

    private final MenuGroupId id;
    private final DisplayedName name;

    public MenuGroup(MenuGroupId id, DisplayedName name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroup(UUID id, String name) {
        this(new MenuGroupId(id), new DisplayedName(name, text -> false));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuGroup)) {
            return false;
        }
        MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MenuGroup{" +
            "id=" + id +
            ", name=" + name +
            '}';
    }
}
