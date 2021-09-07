package kitchenpos.menus.tobe.dto;

import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public class CreateMenuGroupRequest {
    private String name;

    protected CreateMenuGroupRequest() {}

    public CreateMenuGroupRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MenuGroup toMenuGroup() {
        return new MenuGroup(UUID.randomUUID(), name);
    }
}
