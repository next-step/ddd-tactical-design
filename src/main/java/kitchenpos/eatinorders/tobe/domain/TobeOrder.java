package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.application.TobeOrderTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Table(name = "orders")
@Entity
public class TobeOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    private UUID orderTableId;

    private TobeOrderLineItems tobeOrderLineItems;
    protected TobeOrder() {
    }

    public TobeOrder(final UUID id, final OrderStatus status, final LocalDateTime orderDateTime, final UUID orderTableId, final List<TobeOrderLineItem> orderLineItems) {
        this(id, status, orderDateTime, orderTableId, new TobeOrderLineItems(orderLineItems));
    }

    public TobeOrder(final UUID id, final OrderStatus status, final LocalDateTime orderDateTime, final UUID orderTableId, final TobeOrderLineItems orderLineItems) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderTableId = orderTableId;
        this.tobeOrderLineItems = orderLineItems;
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public TobeOrderLineItems getOrderLineItems() {
        return tobeOrderLineItems;
    }

    public Stream<TobeOrderLineItem> getOrderLineItemsStream() {
        return tobeOrderLineItems.getOrderLineItems().stream();
    }

    public void accept() {
        this.status = OrderStatus.ACCEPTED;
    }

    public void served() {
        this.status = OrderStatus.SERVED;
    }

    public void delivering() {
        this.status = OrderStatus.DELIVERING;
    }

    public void delivered() {
        this.status = OrderStatus.DELIVERED;
    }

    public void completed() {
        this.status = OrderStatus.COMPLETED;
    }
}
