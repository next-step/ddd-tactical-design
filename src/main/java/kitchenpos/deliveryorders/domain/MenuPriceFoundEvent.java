package kitchenpos.deliveryorders.domain;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class MenuPriceFoundEvent extends ApplicationEvent {

  private final UUID menuId;
  private final BigDecimal price;

  public MenuPriceFoundEvent(final Object source, final UUID menuId, final BigDecimal price) {
    super(source);
    this.menuId = menuId;
    this.price = price;
  }

  public UUID getMenuId() {
    return menuId;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
