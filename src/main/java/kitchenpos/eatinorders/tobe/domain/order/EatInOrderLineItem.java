package kitchenpos.eatinorders.tobe.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;

@Table(name = "order_line_item")
@Entity
public class EatInOrderLineItem {

  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Column(name = "quantity", nullable = false)
  @Embedded
  private EatInOrderLineItemQuantity quantity;

  @Column(name = "menu_id", nullable = false)
  private UUID menuId;

  @Transient
  private Price price;

  protected EatInOrderLineItem() {
  }

  protected EatInOrderLineItem(EatInOrderLineItemQuantity quantity, UUID menuId, Price price) {
    this.quantity = Objects.requireNonNull(quantity);
    this.menuId = Objects.requireNonNull(menuId);
    this.price = Objects.requireNonNull(price);
  }

  public Long getSeq() {
    return seq;
  }

  public EatInOrderLineItemQuantity getQuantity() {
    return quantity;
  }

  public UUID getMenuId() {
    return menuId;
  }

  public Price getPrice() {
    return price;
  }
}
