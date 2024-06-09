package kitchenpos.takeoutorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.support.domain.OrderLineItem;
import kitchenpos.support.domain.OrderLineItems;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_STATUS;

@Table(name = "takeout_orders")
@Entity
public class TakeoutOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private TakeoutOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    @JoinColumn(
            name = "takeout_order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_takeout_order_line_item_to_orders")
    )
    private OrderLineItems orderLineItems;

    protected TakeoutOrder() {
    }

    protected TakeoutOrder(UUID id, TakeoutOrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems) {
        orderLineItems.checkNegativeQuantity();
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
    }

    public static TakeoutOrder create(OrderLineItems orderLineItems) {
        return new TakeoutOrder(UUID.randomUUID(), TakeoutOrderStatus.WAITING, LocalDateTime.now(), orderLineItems);
    }


    public static TakeoutOrder create(OrderLineItems orderLineItems, TakeoutOrderStatus status) {
        return new TakeoutOrder(UUID.randomUUID(), status, LocalDateTime.now(), orderLineItems);
    }

    public TakeoutOrder accept() {
        changeStatus(TakeoutOrderStatus.WAITING);
        return this;
    }

    public TakeoutOrder serve() {
        changeStatus(TakeoutOrderStatus.ACCEPTED);
        return this;
    }

    public TakeoutOrder complete() {
        changeStatus(TakeoutOrderStatus.SERVED);
        return this;
    }

    public UUID getId() {
        return id;
    }

    public TakeoutOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getValues();
    }

    private void changeStatus(TakeoutOrderStatus status) {
        if (this.status != status) {
            throw new KitchenPosIllegalStateException(INVALID_ORDER_STATUS, status);
        }
        this.status = status.next();
    }
}
