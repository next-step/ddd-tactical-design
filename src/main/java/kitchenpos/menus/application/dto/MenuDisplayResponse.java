package kitchenpos.menus.application.dto;

import java.util.UUID;

public class MenuDisplayResponse {
    private UUID id;
    private boolean displayed;

    public MenuDisplayResponse(UUID id, boolean displayed) {
        this.id = id;
        this.displayed = displayed;
    }

    public UUID getId() {
        return id;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
