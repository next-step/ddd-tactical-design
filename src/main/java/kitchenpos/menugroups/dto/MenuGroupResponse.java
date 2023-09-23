package kitchenpos.menugroups.dto;

import kitchenpos.menugroups.domain.MenuGroupId;

import java.util.UUID;

public class MenuGroupResponse {
    private UUID id;
    private String name;

    public MenuGroupResponse() {
    }

    public MenuGroupResponse(MenuGroupId id, String name) {
        this.id = id.getValue();
        this.name = name;
    }


    public UUID getId() {
        return id;
    }


    public String getName() {
        return name;
    }

}
