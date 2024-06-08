package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.domain.OrderStatus;
import kitchenpos.common.domain.OrderType;
import kitchenpos.eatinorders.application.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;

@Table(name = "orders")
@Entity
public class Order {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private OrderType type;

  @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "order_date_time", nullable = false)
  private LocalDateTime orderDateTime;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(
      name = "order_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
  )
  private List<OrderLineItemRequest> orderLineItemRequests;

  @Column(name = "delivery_address")
  private String deliveryAddress;

  @ManyToOne
  @JoinColumn(
      name = "order_table_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
  )
  private OrderTableRequest orderTableRequest;

  @Transient
  private UUID orderTableId;

  protected Order() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
