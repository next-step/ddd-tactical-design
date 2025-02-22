package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.vo.MenuGroupName;

import java.util.Objects;
import java.util.UUID;

public class MenuGroup {
    private final UUID id;
    private final MenuGroupName name;

    public MenuGroup(final UUID id, final String name) {
        this(id, new MenuGroupName(name));
    }

    public MenuGroup(final UUID id, final MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuGroup that = (MenuGroup) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
