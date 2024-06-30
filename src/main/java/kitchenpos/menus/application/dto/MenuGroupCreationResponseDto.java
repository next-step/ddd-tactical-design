package kitchenpos.menus.application.dto;

import java.util.UUID;
import kitchenpos.menus.domain.menugroup.MenuGroup;

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

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
