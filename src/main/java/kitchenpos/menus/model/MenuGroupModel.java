package kitchenpos.menus.model;

import java.util.UUID;

public class MenuGroupModel {
    private UUID id;
    private String name;

    public MenuGroupModel(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
