package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "eat_in_orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "orders_master", columnDefinition = "binary(16)")
    private UUID orderMasterId;

    @Column(name = "order_table_id", columnDefinition = "binary(16)")
    private UUID orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(UUID orderMasterId, UUID orderTableId) {
        if (orderMasterId == null) {
            throw new IllegalArgumentException("주문 master 가 없으면 등록 할 수 없습니다.");
        }
        if (orderTableId == null) {
            throw new IllegalArgumentException("테이블이 없으면 등록 할 수 없습니다.");
        }
        this.id = UUID.randomUUID();
        this.orderMasterId = orderMasterId;
        this.orderTableId = orderTableId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
