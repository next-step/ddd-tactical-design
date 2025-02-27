package kitchenpos.eatinorders.tobe.domain.common;

import jakarta.persistence.*;
import kitchenpos.eatinorders.tobe.domain.OrderTableId;

import java.time.LocalDateTime;

/*
Order 테이블 매핑용 entity 파일
getter, setter만 존재

비즈니스 관련 로직은
OrderEntity를 필드로 가진 EatInOrder.java 에 구현
*/

@Table(name = "orders")
@Entity
public class OrderEntity {

    @EmbeddedId
    private OrderId id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private OrderLineItems orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_table_id", columnDefinition = "binary(16)")
    @Embedded
    private OrderTableId orderTableId;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    protected OrderEntity() {
    }

    public OrderEntity(OrderType type, OrderStatus status, OrderLineItems orderLineItems, String deliveryAddress, OrderTableId orderTableId) {
        this(OrderId.generate(), type, status, orderLineItems, deliveryAddress, orderTableId, LocalDateTime.now());
    }

    public OrderEntity(OrderId id, OrderType type, OrderStatus status, OrderLineItems orderLineItems, String deliveryAddress, OrderTableId orderTableId, LocalDateTime orderDateTime) {
        this.id = id;
        this.orderType = type;
        this.status = status;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderTableId = orderTableId;
        this.orderDateTime = orderDateTime;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public void changeOrderDateTime(LocalDateTime dateTime) {
        this.orderDateTime = dateTime;
    }

    public void changeOrderTableId(OrderTableId orderTableId) {
        this.orderTableId = orderTableId;
    }

    public OrderId id() {
        return id;
    }

    public OrderType type() {
        return orderType;
    }

    public OrderStatus status() {
        return status;
    }

    public OrderLineItems orderLineItems() {
        return orderLineItems;
    }

    public String deliveryAddress() {
        return deliveryAddress;
    }

    public OrderTableId orderTableId() {
        return orderTableId;
    }

    public LocalDateTime orderDateTime() {
        return orderDateTime;
    }
}
