package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import kitchenpos.eatinorders.model.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderInfo orderInfo;

    @CreationTimestamp
    private LocalDateTime orderedTime;

    @Embedded
    private OrderLineItems orderLineItems;

    protected Order() {
    }

    public Order(Long orderTableId, List<OrderLineItem> orderLineItems) {
        this.orderInfo = new OrderInfo(orderTableId);
        this.orderLineItems = new OrderLineItems(orderLineItems);
    }

    public Order(OrderStatus orderStatus) {
        this.orderInfo = new OrderInfo(orderStatus);
    }

    public Long getId() {
        return id;
    }

    public Long getOrderTableId() {
        return orderInfo.getOrderTableId();
    }

    public OrderStatus getOrderStatus() {
        return orderInfo.getOrderStatus();
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

    public List<Long> getMenuIds() {
        return orderLineItems.getMenuIds();
    }

    public void updateOrderLineItems(List<OrderLineItem> savedOrderLineItems) {
        this.orderLineItems = new OrderLineItems(savedOrderLineItems);
    }

    public void initOrder(Long orderTableId) {
        this.orderInfo = OrderInfo.createCooking(orderTableId);
    }

    public void changeOrderStatus(Order inputOrder) {
        this.orderInfo = inputOrder.orderInfo.changeOrderStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(orderInfo, order.orderInfo) &&
                Objects.equals(orderedTime, order.orderedTime) &&
                Objects.equals(orderLineItems, order.orderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderInfo, orderedTime, orderLineItems);
    }
}
