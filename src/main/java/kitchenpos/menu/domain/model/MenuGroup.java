package kitchenpos.menu.domain.model;

import java.util.UUID;

public class MenuGroup {
    private UUID id;
    private MenuGroupName name;

    public MenuGroup() {
    }

    private MenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroup create(UUID id, MenuGroupName name) {
        return new MenuGroup(id, name);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.value();
    }

    public void setName(final MenuGroupName name) {
        this.name = name;
    }
}
