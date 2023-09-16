package kitchenpos.menus.tobe.application.dto;

import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;

import java.util.UUID;

public class TobeMenuGroupResponse {
    private UUID id;
    private String name;

    public TobeMenuGroupResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TobeMenuGroupResponse of(TobeMenuGroup tobeMenuGroup) {
        return new TobeMenuGroupResponse(tobeMenuGroup.getId(), tobeMenuGroup.getStringName());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
