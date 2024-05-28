package kitchenpos.menus.tobe.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.MenuGroup;

public class MenuGroupsCreationResponseDto {
    private List<MenuGroupCreationResponseDto> menuGroupCreationResponseDtoList;

    public MenuGroupsCreationResponseDto(List<MenuGroupCreationResponseDto> menuGroupCreationResponseDtoList) {
        this.menuGroupCreationResponseDtoList = menuGroupCreationResponseDtoList;
    }

    public static MenuGroupsCreationResponseDto of(List<MenuGroup> menuGroups) {
        final List<MenuGroupCreationResponseDto> list = new ArrayList<>();

        for (MenuGroup menuGroup : menuGroups) {
            final MenuGroupCreationResponseDto creationResponseDto =
                    MenuGroupCreationResponseDto.of(menuGroup);
            list.add(creationResponseDto);
        }

        return new MenuGroupsCreationResponseDto(list);
    }
}
