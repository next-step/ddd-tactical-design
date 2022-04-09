package kitchenpos.eatinorders.tobe.domain.dto;

import java.math.BigDecimal;

public class MenuRes {

  private final Long menuId;

  private final String menuName;

  private final BigDecimal menuPrice;

  public MenuRes(Long menuId, String menuName, BigDecimal menuPrice) {
    this.menuId = menuId;
    this.menuName = menuName;
    this.menuPrice = menuPrice;
  }

  public Long getMenuId() {
    return menuId;
  }

  public String getMenuName() {
    return menuName;
  }

  public BigDecimal getMenuPrice() {
    return menuPrice;
  }
}
