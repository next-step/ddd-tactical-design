package kitchenpos.deliveryorders.domain;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Embeddable
public class DeliveryOrderLineItems {
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(
      name = "order_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders"))
  private List<DeliveryOrderLineItem> orderLineItems;

  protected DeliveryOrderLineItems() {}

  protected DeliveryOrderLineItems(List<DeliveryOrderLineItem> orderLineItems) {
    if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
      throw new IllegalArgumentException();
    }

    this.orderLineItems = orderLineItems;
  }

  public List<DeliveryOrderLineItem> getOrderLineItems() {
    return orderLineItems;
  }

  public void add(final DeliveryOrderLineItem orderLineItem) {
    this.orderLineItems.add(orderLineItem);
  }
}
