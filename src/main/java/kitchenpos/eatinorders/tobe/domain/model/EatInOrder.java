package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.common.Order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {

    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;
    @Column(name = "order_table_id", columnDefinition = "binary(16)")
    private UUID orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(UUID orderTableId) {
        super(LocalDateTime.now());
        this.status = EatInOrderStatus.WAITING;
        this.orderTableId = orderTableId;
    }

    public boolean orderTableEq(OrderTable orderTable) {
        return orderTableId.equals(orderTable.getId());
    }

    public boolean statusNotEq(EatInOrderStatus status) {
        return this.status != status;
    }
}
