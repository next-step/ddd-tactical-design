package kitchenpos.menus.tobe.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuChangePriceRequestDto {
  private UUID menuId;
  private BigDecimal menuPrice;

  public UUID getMenuId() {
    return menuId;
  }

  public BigDecimal getMenuPrice() {
    return menuPrice;
  }
}
