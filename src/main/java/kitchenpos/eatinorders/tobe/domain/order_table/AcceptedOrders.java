package kitchenpos.eatinorders.tobe.domain.order_table;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import kitchenpos.eatinorders.domain.OrderStatus;

public class AcceptedOrders {
  @OneToMany
  @JoinColumn(
      name = "order_table_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
  )
  private List<AcceptedOrder> orders;

  protected AcceptedOrders() {
  }

  public boolean existsIncompleteOrder() {
    return orders.stream()
        .anyMatch(order -> !order.getStatus().equals(OrderStatus.COMPLETED));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AcceptedOrders that = (AcceptedOrders) o;
    return Objects.equals(orders, that.orders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orders);
  }

  @Override
  public String toString() {
    return "AcceptedOrders{" +
        "orders=" + orders +
        '}';
  }
}
