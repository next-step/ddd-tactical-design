package kitchenpos.eatinorders.tobe.domain.dto;

import java.math.BigDecimal;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;

public class OrderLineItemReq {

  private final Long menuId;

  private final BigDecimal price;

  private final long quantity;

  public OrderLineItemReq(Long menuId, BigDecimal price, long quantity) {
    this.menuId = menuId;
    this.price = price;
    this.quantity = quantity;
  }

  public Long getMenuId() {
    return menuId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public long getQuantity() {
    return quantity;
  }

  public boolean isInvalidPrice(MenuRes menu) {
    return !price.equals(menu.getMenuPrice());
  }

  public EatInOrderLineItem toOrderLineItem(MenuRes menu) {
    return new EatInOrderLineItem(menuId, menu.getMenuName(), quantity, price);
  }
}
