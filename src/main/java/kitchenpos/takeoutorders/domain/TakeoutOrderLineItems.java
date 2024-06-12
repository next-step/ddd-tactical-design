package kitchenpos.takeoutorders.domain;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Embeddable
public class TakeoutOrderLineItems {
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(
      name = "order_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders"))
  private List<TakeoutOrderLineItem> orderLineItems;

  protected TakeoutOrderLineItems() {}

  protected TakeoutOrderLineItems(List<TakeoutOrderLineItem> orderLineItems) {
    if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
      throw new IllegalArgumentException();
    }

    this.orderLineItems = orderLineItems;
  }

  public List<TakeoutOrderLineItem> getOrderLineItems() {
    return orderLineItems;
  }

  public void add(final TakeoutOrderLineItem orderLineItem) {
    this.orderLineItems.add(orderLineItem);
  }
}
