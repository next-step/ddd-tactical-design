package kitchenpos.eatinorders.tobe.domain.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.order.event.OrderCompletionEvent;
import kitchenpos.supports.domain.tobe.OrderType;
import org.springframework.data.domain.AbstractAggregateRoot;

@Table(name = "orders")
@Entity
public class EatInOrder extends AbstractAggregateRoot<EatInOrder> {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private OrderType type;

  @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private EatInOrderStatus status;

  @Column(name = "order_date_time", nullable = false)
  private LocalDateTime orderDateTime;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(
      name = "order_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
  )
  private List<EatInOrderLineItem> orderLineItems;

  @Column(name = "order_table_id", nullable = false)
  private UUID orderTableId;

  protected EatInOrder() {
  }

  protected EatInOrder(UUID orderTableId, List<EatInOrderLineItem> orderLineItems) {
    this.id = UUID.randomUUID();
    this.type = OrderType.EAT_IN;
    this.status = EatInOrderStatus.WAITING;
    this.orderDateTime = LocalDateTime.now();
    this.orderTableId = orderTableId;
    this.orderLineItems = Collections.unmodifiableList(orderLineItems);
  }

  public void accept() {
    if (this.status != EatInOrderStatus.WAITING) {
      throw new IllegalStateException();
    }
    this.status = EatInOrderStatus.ACCEPTED;
  }

  public void serve() {
    if (this.status != EatInOrderStatus.ACCEPTED) {
      throw new IllegalStateException();
    }
    this.status = EatInOrderStatus.SERVED;
  }

  public void complete() {
    if (this.status != EatInOrderStatus.SERVED) {
      throw new IllegalStateException();
    }
    this.status = EatInOrderStatus.COMPLETED;
    registerEvent(new OrderCompletionEvent(this.orderTableId));
  }

  public UUID getId() {
    return id;
  }

  public OrderType getType() {
    return type;
  }

  public EatInOrderStatus getStatus() {
    return status;
  }

  public LocalDateTime getOrderDateTime() {
    return orderDateTime;
  }

  public List<EatInOrderLineItem> getOrderLineItems() {
    return orderLineItems;
  }

  public UUID getOrderTableId() {
    return orderTableId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EatInOrder that = (EatInOrder) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
