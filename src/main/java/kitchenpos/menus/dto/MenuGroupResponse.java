package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public record MenuGroupResponse(
        UUID id, String name
) {
    public static MenuGroupResponse of(MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getName());
    }
}
