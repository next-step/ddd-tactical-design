package kitchenpos.menus.dto;

import java.util.UUID;

public final class MenuGroupDetailResponse {
    private final UUID id;
    private final String name;

    public MenuGroupDetailResponse(UUID id, String name) {
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
