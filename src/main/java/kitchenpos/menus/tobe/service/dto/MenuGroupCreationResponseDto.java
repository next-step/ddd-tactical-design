package kitchenpos.menus.tobe.service.dto;

import io.micrometer.observation.ObservationTextPublisher;
import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupCreationResponseDto {
    private final UUID id;
    private final String name;

    public MenuGroupCreationResponseDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupCreationResponseDto of(MenuGroup menuGroup) {
        return new MenuGroupCreationResponseDto(menuGroup.getId(), menuGroup.getName());
    }
}
