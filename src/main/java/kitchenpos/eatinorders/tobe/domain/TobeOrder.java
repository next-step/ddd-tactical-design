package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class TobeOrder {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private TobeOrderLineItems orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(
        name = "order_table_id",
        columnDefinition = "varbinary(16)",
        nullable = false
    )
    private UUID orderTableId;

    protected TobeOrder() {
    }

    public TobeOrder(OrderType type, TobeOrderLineItems orderLineItems, String deliveryAddress, UUID orderTableId) {
        validationOrderType(type);
        validationDeilveryAddress(type, deliveryAddress);
        validationEatInOrderTableId(type, orderTableId);
        this.id = UUID.randomUUID();
        this.type = type;
        this.status = OrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderTableId = orderTableId;
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<TobeOrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void accept(KitchenridersClient kitchenridersClient) {
        validationAcceptOrderStatus();
        if (type == OrderType.DELIVERY) {
            kitchenridersClient.requestDelivery(id, orderLineItems.itemsTotalPrice(), deliveryAddress);
        }
        status = OrderStatus.ACCEPTED;
    }

    private void validationAcceptOrderStatus() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException("주문 대기 상태만 확인이 가능합니다.");
        }
    }

    private void validationEatInOrderTableId(OrderType type, UUID orderTableId) {
        if (type == OrderType.EAT_IN && Objects.isNull(orderTableId)) {
            throw new IllegalArgumentException("주문테이블이 없습니다.");
        }
    }

    private void validationDeilveryAddress(OrderType type, String deliveryAddress) {
        if (type == OrderType.DELIVERY &&
                (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty())) {
            throw new IllegalArgumentException("배송지가 없습니다.");
        }
    }

    private void validationOrderType(OrderType type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
    }
}
