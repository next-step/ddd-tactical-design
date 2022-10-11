package kitchenpos.menus.ui.response;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.domain.MenuGroup;

public class MenuGroupResponse {

    private final UUID id;
    private final String name;

    public MenuGroupResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupResponse from(MenuGroup menuGroup) {
        return new MenuGroupResponse(
            menuGroup.getId(),
            menuGroup.getNameValue()
        );
    }

    public static List<MenuGroupResponse> of(List<MenuGroup> menuGroups) {
        return menuGroups.stream()
            .map(MenuGroupResponse::from)
            .collect(toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
