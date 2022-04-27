package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.util.StringUtils;

@Table(name = "orders")
@Entity
public class Order {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @GeneratedValue
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
    private OrderLineItems orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected Order() { }

    public Order(OrderLineItems orderLineItems, String deliveryAddress) {
        validDeliveryOrder(orderLineItems, deliveryAddress);
        this.type = OrderType.DELIVERY;
        this.status = OrderStatus.WAITING;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public static Order createDeliveryOrder(OrderLineItems orderLineItems, String deliveryAddress) {
        return new Order(orderLineItems, deliveryAddress);
    }

    private void validDeliveryOrder(OrderLineItems orderLineItems, String deliveryAddress) {
        if (orderLineItems.hasNegativeQuantity()) {
            throw new IllegalArgumentException("배달 주문의 각 주문 메뉴당 주문 수량은 최소 0개 이상이어야 합니다.");
        }

        if (StringUtils.isBlank(deliveryAddress)) {
            throw new IllegalArgumentException("배달 주문엔 배달주소가 반드시 포함되어야 합니다.");
        }
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

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    @PrePersist
    private void onPersist() {
        orderDateTime = LocalDateTime.now();
    }
}
