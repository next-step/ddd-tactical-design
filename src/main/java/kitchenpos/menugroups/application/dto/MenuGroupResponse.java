package kitchenpos.menugroups.application.dto;

import kitchenpos.menugroups.domain.MenuGroupId;

import java.util.UUID;

public class MenuGroupResponse {
    final private UUID id;
    final private String name;

    public MenuGroupResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
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
