package kitchenpos.deliveryorders.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class DeliveryOrderLineItem {
  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Embedded private Quantity quantity;

  @Transient private UUID menuId;

  protected DeliveryOrderLineItem() {}

  protected DeliveryOrderLineItem(Long seq, long quantity, UUID menuId) {
    this.seq = seq;
    this.quantity = new Quantity(quantity);
    this.menuId = menuId;
  }

  public static DeliveryOrderLineItem createItem(long quantity, UUID menuId) {
    return new DeliveryOrderLineItem(null, quantity, menuId);
  }

  public Long getSeq() {
    return seq;
  }

  public Quantity getQuantity() {
    return quantity;
  }

  public UUID getMenuId() {
    return menuId;
  }
}
