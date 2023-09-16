package kitchenpos.menugroups.dto;

import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.domain.MenuGroupId;

public class MenuGroupResponse {
    private MenuGroupId id;
    private String name;

    public MenuGroupResponse() {
    }

    public MenuGroupResponse(MenuGroupId id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupResponse fromEntity(MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getNameValue());
    }

    public MenuGroupId getId() {
        return id;
    }

    public void setId(MenuGroupId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
