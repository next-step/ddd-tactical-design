package kitchenpos.takeoutorders.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class TakeoutOrderLineItem {
  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Embedded private Quantity quantity;
  @Transient private UUID menuId;

  protected TakeoutOrderLineItem() {}

  protected TakeoutOrderLineItem(Long seq, long quantity, UUID menuId) {
    this.seq = seq;
    this.quantity = new Quantity(quantity);
    this.menuId = menuId;
  }

  public static TakeoutOrderLineItem createItem(long quantity, UUID menuId) {
    return new TakeoutOrderLineItem(null, quantity, menuId);
  }

  public Long getSeq() {
    return seq;
  }

  public long getQuantity() {
    return quantity.getQuantity();
  }

  public UUID getMenuId() {
    return menuId;
  }
}
