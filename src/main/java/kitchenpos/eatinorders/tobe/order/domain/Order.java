package kitchenpos.eatinorders.tobe.order.domain;

import kitchenpos.eatinorders.tobe.order.domain.exception.OrderAlreadyCompletedException;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_table_id")
    private Long tableId;

    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "ordered_time")
    @CreationTimestamp
    private LocalDateTime orderedTime;

    @Embedded
    private OrderLines orderLines;

    protected Order() {
    }

    public Order(final Long tableId, List<OrderLine> orderLines) {
        validateTableId(tableId);
        this.tableId = tableId;
        this.status = OrderStatus.COOKING;
        this.orderLines = new OrderLines(orderLines);
    }

    private void validateTableId(final Long tableId) {
        if (tableId == null || tableId < 1) {
            throw new IllegalArgumentException("테이블을 입력해야합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Long getTableId() {
        return tableId;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines.get();
    }

    void changeStatus(OrderStatus status) {
        if (this.status == OrderStatus.COMPLETION) {
            throw new OrderAlreadyCompletedException("이미 완료된 주문의 주문 상태를 변경할 수 없습니다.");
        }
        this.status = status;
    }
}
