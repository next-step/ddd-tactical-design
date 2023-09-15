package kitchenpos.eatinorders.domain.order;

import kitchenpos.common.domain.OrderDateTime;
import kitchenpos.ordertables.domain.OrderTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {
    @EmbeddedId
    private EatInOrderId id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Embedded
    private OrderDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems eatInOrderLineItems;

    @Embedded
    private OrderTableId orderTableId;

    protected EatInOrder() {
    }

    public EatInOrderId getId() {
        return id;
    }

    public UUID getIdValue() {
        return id.getId();
    }

}
