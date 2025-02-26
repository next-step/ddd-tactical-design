package kitchenpos.menu.ui.dto;

import java.util.UUID;
import kitchenpos.menu.application.dto.MenuGroupServiceRs;
import kitchenpos.menu.domain.model.MenuGroup;

public class MenuGroupRs {
    private UUID id;
    private String name;

    public MenuGroupRs(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupRs(MenuGroupServiceRs menuGroup) {
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
