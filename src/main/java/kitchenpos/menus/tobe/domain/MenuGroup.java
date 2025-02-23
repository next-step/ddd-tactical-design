package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.vo.MenuGroupId;
import kitchenpos.menus.tobe.domain.vo.MenuGroupName;

import java.util.Objects;
import java.util.UUID;

public class MenuGroup {

    private final MenuGroupId id;
    private final MenuGroupName name;

    public MenuGroup(final UUID id, final String name) {
        this(new MenuGroupId(id), new MenuGroupName(name));
    }

    public MenuGroup(final MenuGroupId id, final MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public UUID id() {
        return id.getValue();
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
