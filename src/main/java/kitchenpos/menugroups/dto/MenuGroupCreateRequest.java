package kitchenpos.menugroups.dto;

import kitchenpos.menugroups.domain.MenuGroup;

public class MenuGroupCreateRequest {
    private String name;

    public MenuGroupCreateRequest() {
    }

    public MenuGroup toEntity() {
        return new MenuGroup(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
