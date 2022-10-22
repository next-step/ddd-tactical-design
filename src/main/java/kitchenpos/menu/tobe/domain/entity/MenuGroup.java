package kitchenpos.menu.tobe.domain.entity;

import java.util.UUID;
import kitchenpos.common.name.Name;

public class MenuGroup {

    public final UUID id;

    public final Name name;

    public MenuGroup(final UUID id, final Name name) {
        this.id = id;
        this.name = name;
    }
}
