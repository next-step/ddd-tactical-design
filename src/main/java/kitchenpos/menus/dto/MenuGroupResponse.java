package kitchenpos.menus.dto;

import java.util.UUID;

public class MenuGroupResponse {
    private UUID menuGroupId;
    private String name;

    public MenuGroupResponse() {
    }

    public MenuGroupResponse(UUID menuGroupId, String name) {
        this.menuGroupId = menuGroupId;
        this.name = name;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
