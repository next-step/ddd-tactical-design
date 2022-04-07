package kitchenpos.eatinorders.tobe.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class TableOrder {

    private UUID id;
    private OrderStatus status;
    private OrderTable table;
    private OrderLineItems items;
    private LocalDateTime orderDateTime;

    public TableOrder(OrderTable table, OrderLineItems items) {
        validate(table);
        this.id = UUID.randomUUID();
        this.status = OrderStatus.WAITING;
        this.table = table;
        this.items = items;
        this.orderDateTime = LocalDateTime.now();
    }

    private void validate(OrderTable table) {
        if (table.isEmpty()) {
            throw new IllegalArgumentException("매장 주문을 이용하기 위해서는 반드시 테이블이 선택되어야 합니다.");
        }
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException("주문이 대기 상태가 아닙니다.");
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("주문이 수락 상태가 아닙니다.");
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException("주문이 준비완료 상태가 아닙니다.");
        }
        this.status = OrderStatus.COMPLETED;
        this.table.clear();
    }
}
