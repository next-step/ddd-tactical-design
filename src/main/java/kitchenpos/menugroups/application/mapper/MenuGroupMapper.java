package kitchenpos.menugroups.application.mapper;

import kitchenpos.menugroups.application.dto.MenuGroupCreateRequest;
import kitchenpos.menugroups.application.dto.MenuGroupResponse;
import kitchenpos.menugroups.domain.MenuGroup;

import java.util.List;
import java.util.stream.Collectors;

public class MenuGroupMapper {

    public static MenuGroup toEntity(MenuGroupCreateRequest request) {
        return new MenuGroup(request.getName());
    }

    public static MenuGroupResponse toDto(MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getNameValue());
    }

    public static List<MenuGroupResponse> toDtos(List<MenuGroup> menuGroups) {
        return menuGroups.stream()
                .map(MenuGroupMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
