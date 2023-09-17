package kitchenpos.menus.application.dto;

import java.util.UUID;

public class MenuGroupInfoResponse {
    private final UUID id;
    private final String name;

    public MenuGroupInfoResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
