package kitchenpos.eatinorders.tobe;

import java.util.UUID;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Order {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private OrderLineItems orderLineItems;

    @Embedded
    private DeliveryAddress deliveryAddress;

    @Transient
    private UUID orderTableId;

    protected Order() {
    }

    private Order(OrderType type, OrderStatus status,
            OrderLineItems orderLineItems, DeliveryAddress deliveryAddress, UUID orderTableId) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.status = status;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderTableId = orderTableId;
    }

    public static Order createEatInOrder(OrderLineItems orderLineItems, UUID orderTableId) {
        return new Order(OrderType.EAT_IN, OrderStatus.WAITING, orderLineItems, DeliveryAddress.empty(), orderTableId);
    }

    public void accept() {
        if (!status.equals(OrderStatus.WAITING)) {
            throw new IllegalArgumentException();
        }
        status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (!status.equals(OrderStatus.ACCEPTED)) {
            throw new IllegalArgumentException();
        }
        status = OrderStatus.SERVED;
    }

    public void complete() {
        if (!status.equals(OrderStatus.SERVED)) {
            throw new IllegalArgumentException();
        }
        status = OrderStatus.COMPLETED;
    }

    public OrderStatus getStatus() {
        return status;
    }
}

