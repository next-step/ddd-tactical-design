package kitchenpos.menus.dto;

import kitchenpos.common.values.Name;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.ToBeMenuGroup;

import java.util.UUID;

public class MenuGroupDto {

  private UUID id;
  private Name name;

  public MenuGroupDto(UUID id, Name name) {
    this.id = id;
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public Name getName() {
    return name;
  }

  public static MenuGroupDto from(ToBeMenuGroup savedResult) {
    return new MenuGroupDto(savedResult.getId(), savedResult.getName());
  }

}
