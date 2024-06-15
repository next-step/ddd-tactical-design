package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.*;
import kitchenpos.common.domain.Price;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Column(name = "quantity", nullable = false)
  private long quantity;

  @Column(name = "menu_id", nullable = false)
  private UUID menuId;

  @Embedded
  private Price price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
          name = "order_id",
          columnDefinition = "binary(16)",
          foreignKey = @ForeignKey(name = "fk_order_line_items_to_orders")
  )
  private Order order;

  protected OrderLineItem() {
  }

  private OrderLineItem(final UUID menuId, final Price price, final Long quantity) {
    validate(menuId, quantity);

    this.quantity = quantity;
    this.price = price;

  }

  public static OrderLineItem of(final UUID menuId, final Long price, final Long quantity) {
    return new OrderLineItem(menuId, Price.from(price), quantity);
  }

  protected void setOrder(final Order order){
    this.order = order;

  }
  private void validate(final UUID uuid, final Long quantity) {
    if (Objects.isNull(uuid) || Objects.isNull(quantity)) {
      throw new RuntimeException("메뉴 혹은 상품 수량이 비정상이라면 등록할 수 없다.");
    }
  }

  protected UUID getMenuId() {
    return menuId;
  }

  protected BigDecimal getPrice() {
    return price.getPrice();
  }

  protected Long getQuantity() {
    return quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderLineItem that = (OrderLineItem) o;
    return Objects.equals(seq, that.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq);
  }
}
