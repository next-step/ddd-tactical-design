package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequestDto {
  private String name;
  private BigDecimal price;
  private List<MenuProductCreateRequestDto> menuProductCreateRequestDtos;
  private UUID menuGroupId;
  private Boolean displayed;

  public MenuCreateRequestDto(
      String name,
      BigDecimal price,
      List<MenuProductCreateRequestDto> menuProductCreateRequestDtos,
      UUID menuGroupId,
      Boolean displayed) {
    this.name = name;
    this.price = price;
    this.menuProductCreateRequestDtos = menuProductCreateRequestDtos;
    this.menuGroupId = menuGroupId;
    this.displayed = displayed;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public List<MenuProductCreateRequestDto> getMenuProductCreateRequestDtos() {
    return menuProductCreateRequestDtos;
  }

  public UUID getMenuGroupId() {
    return menuGroupId;
  }

  public Boolean getDisplayed() {
    return displayed;
  }
}
