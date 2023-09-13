package kitchenpos.menus.domain.model;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.vo.MenuGroupName;

import java.util.UUID;

public class MenuGroupModel {
    private final UUID id;

    private final MenuGroupName name;

    public MenuGroupModel(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupModel(MenuGroup menuGroup) {
        this(menuGroup.getId(), new MenuGroupName(menuGroup.getName()));
    }

    public UUID getId() {
        return id;
    }

    public MenuGroupName getName() {
        return name;
    }

    public MenuGroupModel of(MenuGroup menuGroup) {
        return new MenuGroupModel(menuGroup.getId(), new MenuGroupName(menuGroup.getName()));
    }

    public MenuGroup toMenuGroup() {
        return new MenuGroup(id, name.getName());
    }

}
