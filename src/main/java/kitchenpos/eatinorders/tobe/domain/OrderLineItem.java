package kitchenpos.eatinorders.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Column(name = "menu_id", nullable = false)
  private UUID menuId;

  @Embedded
  private Price price;

  @Embedded
  private Quantity quantity;

  protected OrderLineItem() {

  }

  public OrderLineItem(UUID menuId, BigDecimal price, long quantity) {
    this(menuId, Price.from(price), Quantity.from(quantity));
  }

  public OrderLineItem(UUID menuId, Price price, Quantity quantity) {
    if (Objects.isNull(menuId)) {
      throw new IllegalArgumentException();
    }
    this.menuId = menuId;
    this.price = price;
    this.quantity = quantity;
  }

  public UUID getMenuId() {
    return menuId;
  }
}
