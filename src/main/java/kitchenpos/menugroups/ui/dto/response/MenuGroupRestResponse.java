package kitchenpos.menugroups.ui.dto.response;

import kitchenpos.menugroups.domain.MenuGroupId;

import java.util.UUID;

public class MenuGroupRestResponse {
    final private UUID id;
    final private String name;

    public MenuGroupRestResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupRestResponse(MenuGroupId id, String name) {
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
