package kitchenpos.menugroups.application.mapper;

import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.dto.MenuGroupResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MenuGroupMapper {

    public static MenuGroupResponse toDto(MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getNameValue());
    }

    public static List<MenuGroupResponse> toDtos(List<MenuGroup> menuGroups) {
        return menuGroups.stream()
                .map(MenuGroupMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
