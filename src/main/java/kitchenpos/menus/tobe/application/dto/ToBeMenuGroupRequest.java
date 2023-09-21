package kitchenpos.menus.tobe.application.dto;

import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;

import java.util.UUID;

public class ToBeMenuGroupRequest {
    private final UUID id;
    private final String name;

    public ToBeMenuGroupRequest(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ToBeMenuGroupRequest from(TobeMenuGroup tobeMenuGroup) {
        return new ToBeMenuGroupRequest(tobeMenuGroup.getId(), tobeMenuGroup.getStringName());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
