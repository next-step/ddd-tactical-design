package kitchenpos.menu.application.dto;

import java.util.UUID;
import kitchenpos.menu.domain.model.MenuGroup;

public class MenuGroupServiceRs {
    private UUID id;
    private String name;

    public MenuGroupServiceRs(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupServiceRs(MenuGroup menuGroup) {
        this.id = menuGroup.getId();
        this.name = menuGroup.getName();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
