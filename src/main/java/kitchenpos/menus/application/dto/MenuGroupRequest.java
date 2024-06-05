package kitchenpos.menus.application.dto;

import java.util.UUID;


public class MenuGroupRequest {

    private UUID id;

    private String name;

    public MenuGroupRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
