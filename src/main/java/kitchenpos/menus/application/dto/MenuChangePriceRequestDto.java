package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuChangePriceRequestDto {
  private UUID menuId;
  private BigDecimal menuPrice;

  public MenuChangePriceRequestDto(UUID menuId, BigDecimal menuPrice) {
    this.menuId = menuId;
    this.menuPrice = menuPrice;
  }

  public UUID getMenuId() {
    return menuId;
  }

  public BigDecimal getMenuPrice() {
    return menuPrice;
  }
}
