package kitchenpos.eatinorders.domain;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Embeddable
public class EatInOrderLineItems {
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(
      name = "order_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders"))
  private List<EatInOrderLineItem> orderLineItems;

  protected EatInOrderLineItems() {}

  protected EatInOrderLineItems(List<EatInOrderLineItem> orderLineItems) {
    if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
      throw new IllegalArgumentException();
    }

    this.orderLineItems = orderLineItems;
  }

  public List<EatInOrderLineItem> getOrderLineItems() {
    return orderLineItems;
  }

  public void add(final EatInOrderLineItem orderLineItem) {
    this.orderLineItems.add(orderLineItem);
  }
}
