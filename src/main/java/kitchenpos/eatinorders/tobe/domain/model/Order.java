package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.common.domain.model.OrderStatus;
import kitchenpos.common.domain.model.OrderType;
import kitchenpos.eatinorders.tobe.domain.validator.OrderTableValidator;
import kitchenpos.eatinorders.tobe.domain.validator.OrderValidator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private OrderType orderType;

    private OrderStatus orderStatus;

    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    private UUID orderTableId;

    protected Order() {

    }

    public Order(OrderType orderType, OrderStatus orderStatus, LocalDateTime orderDateTime, OrderLineItems orderLineItems, UUID orderTableId, OrderValidator orderValidator) {
        orderValidator.checkEmptyOrderLineItems(orderLineItems);
        orderValidator.checkOrderLineItemQuantity(orderLineItems, orderType);
        orderValidator.checkTableStatus(orderTableId);

        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.orderTableId = orderTableId;
    }

    public void advanceOrderStatus(OrderTableValidator orderTableValidator, OrderValidator orderValidator) {
        orderValidator.advanceOrderStatus(this);
        if (this.orderStatus == OrderStatus.COMPLETED && this.orderType == OrderType.EAT_IN) {
            orderTableValidator.makeEmptyTable(orderTableId);
        }
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public boolean isCompleted() {
        return this.orderStatus == OrderStatus.COMPLETED;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
