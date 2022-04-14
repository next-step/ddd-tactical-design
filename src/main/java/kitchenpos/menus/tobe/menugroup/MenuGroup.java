package kitchenpos.menus.tobe.menugroup;

import java.util.Objects;
import java.util.UUID;

public class MenuGroup {
    private UUID id;
    private MenuGroupName name;

    public MenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroup(String name) {
        this(UUID.randomUUID(), new MenuGroupName(name));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuGroup menuGroup = (MenuGroup) o;

        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
