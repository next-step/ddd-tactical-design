package kitchenpos.eatinorders.tobe.domain.order.createsupporter;

import java.util.Objects;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;

public class OrderedMenu {

  private final UUID menuId;
  private final boolean displayed;
  private final Price price;

  public OrderedMenu(UUID menuId, boolean displayed, Price price) {
    this.menuId = Objects.requireNonNull(menuId);
    this.displayed = displayed;
    this.price = Objects.requireNonNull(price);
  }

  public UUID getMenuId() {
    return menuId;
  }

  public boolean isDisplayed() {
    return displayed;
  }

  public Price getPrice() {
    return price;
  }
}
