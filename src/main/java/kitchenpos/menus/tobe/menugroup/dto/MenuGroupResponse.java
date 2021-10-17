package kitchenpos.menus.tobe.menugroup.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.menugroup.domain.model.MenuGroupV2;

public class MenuGroupResponse {

    private UUID id;
    private String name;

    protected MenuGroupResponse() {
    }

    private MenuGroupResponse(final MenuGroupV2 menuGroupV2) {
        this.id = menuGroupV2.getId();
        this.name = menuGroupV2.getName().getName();
    }

    public static MenuGroupResponse from(final MenuGroupV2 menuGroupV2) {
        return new MenuGroupResponse(menuGroupV2);
    }

    public static List<MenuGroupResponse> from(final List<MenuGroupV2> menuGroupV2s) {
        return menuGroupV2s.stream()
            .map(MenuGroupResponse::from)
            .collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
