package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.vo.Name;

import java.util.Objects;
import java.util.UUID;

public class MenuGroup {
    private final UUID id;
    private final Name name;

    public MenuGroup(final UUID id, final String name) {
        this(id, new Name(name));
    }

    public MenuGroup(final UUID id, final Name name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
