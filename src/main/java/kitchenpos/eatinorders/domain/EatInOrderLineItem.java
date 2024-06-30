package kitchenpos.eatinorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class EatInOrderLineItem {
  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Column(name = "quantity", nullable = false)
  private long quantity;

  @Transient private UUID menuId;

  protected EatInOrderLineItem() {}

  protected EatInOrderLineItem(final Long seq, final long quantity, final UUID menuId) {
    if (menuId == null) {
      throw new IllegalArgumentException();
    }

    this.seq = seq;
    this.quantity = quantity;
    this.menuId = menuId;
  }

  public static EatInOrderLineItem createItem(long quantity, UUID menuId) {
    return new EatInOrderLineItem(null, quantity, menuId);
  }

  public Long getSeq() {
    return seq;
  }

  public long getQuantity() {
    return quantity;
  }

  public UUID getMenuId() {
    return menuId;
  }
}
