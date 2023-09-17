package kitchenpos.menugroups.dto;

import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.domain.MenuGroupId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuGroupResponse {
    private UUID id;
    private String name;

    public MenuGroupResponse() {
    }

    public MenuGroupResponse(MenuGroupId id, String name) {
        this.id = id.getValue();
        this.name = name;
    }

    public static MenuGroupResponse fromEntity(MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getNameValue());
    }

    public static List<MenuGroupResponse> fromEntities(List<MenuGroup> menuGroups) {
        return menuGroups.stream()
                .map(MenuGroupResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public UUID getId() {
        return id;
    }


    public String getName() {
        return name;
    }

}
