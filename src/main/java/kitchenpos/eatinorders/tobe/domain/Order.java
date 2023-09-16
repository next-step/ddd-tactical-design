package kitchenpos.eatinorders.tobe.domain;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Order {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    @Embedded
    private OrderLineItems orderLineItems = new OrderLineItems();

    public Order(OrderTable orderTable, OrderLineItems orderLineItems) {
        checkOrerTableIsVacant(orderTable);
        checkOrderLineItemsIsEmpty(orderLineItems);

        orderLineItems.mapOrder(this);
        this.id = UUID.randomUUID();
        this.status = OrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderTable = orderTable;
        this.orderLineItems = orderLineItems;
    }

    protected Order() {
    }

    private void checkOrderLineItemsIsEmpty(OrderLineItems orderLineItems) {
        if (isEmpty(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문내역이 하나 이상 있어야 합니다");
        }
    }

    private void checkOrerTableIsVacant(OrderTable orderTable) {
        if (isEmpty(orderTable)) {
            throw new IllegalArgumentException("매장주문엔 주문테이블이 필수입니다");
        }

        if (orderTable.isVacant()) {
            throw new IllegalStateException("주문테이블이 이용중이 아닙니다");
        }
    }

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException("접수 대기 중인 주문만 수락할수 있습니다");
        }

        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("수락된 주문만 전달할수 있습니다");
        }

        this.status = OrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException("전달된 주문만 완료할수 있습니다");
        }

        this.status = OrderStatus.COMPLETED;
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

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

    public enum OrderStatus {
        WAITING, ACCEPTED, SERVED, COMPLETED
    }
}
