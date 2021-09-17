package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinordertables.domain.OrderTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.OrderStatus.WAITING;
import static kitchenpos.eatinorders.tobe.domain.OrderType.DELIVERY;
import static kitchenpos.eatinorders.tobe.domain.OrderType.EAT_IN;

@Table(name = "orders")
@Entity(name = "TobeOrder")
public class Order {
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(
            name = "order_table_id",
            columnDefinition = "varbinary(16)",
            nullable = false
    )
    private UUID orderTableId;

    protected Order() {}

    public Order(final OrderType type, final List<OrderLineItem> orderLineItems, final String deliveryAddress, final UUID orderTableId, final OrderTableTranslator orderTableTranslator) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("type 은 필수값입니다.");
        }

        if (type == DELIVERY) {
            if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
                throw new IllegalArgumentException("배달 주문은 deliveryAddress 가 필수값이어야 합니다.");
            }
        }
        if (type == EAT_IN) {
            final OrderTable orderTable = orderTableTranslator.getOrderTable(orderTableId);
            if (orderTable.isEmpty()) {
                throw new IllegalStateException("비어있는 테이블에는 주문을 추가할 수 없습니다.");
            }
        }
        this.id = UUID.randomUUID();
        this.type = type;
        this.status = WAITING;
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

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void changeStatus(final OrderStatus status) {
        this.status = status;
    }
}
