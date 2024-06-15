package kitchenpos.deliveryorders.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
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
  @Transient private Price price;

  protected DeliveryOrderLineItem() {}

  protected DeliveryOrderLineItem(Long seq, long quantity, UUID menuId, BigDecimal price) {
    this.seq = seq;
    this.quantity = new Quantity(quantity);
    this.menuId = menuId;
    this.price = new Price(price);
  }

  public static DeliveryOrderLineItem createItem(long quantity, UUID menuId, BigDecimal price) {
    return new DeliveryOrderLineItem(null, quantity, menuId, price);
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

  public BigDecimal getPrice() {
    return price.getPrice();
  }
}
