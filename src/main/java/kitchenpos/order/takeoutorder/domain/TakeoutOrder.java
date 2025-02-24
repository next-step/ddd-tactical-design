package kitchenpos.order.takeoutorder.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderType;

@Entity
@DiscriminatorValue("TAKEOUT")
public class TakeoutOrder extends Order {

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private TakeoutOrderStatus status;

    public TakeoutOrder() {
    }

    @Override
    public OrderType getType() {
        return OrderType.TAKEOUT;
    }

    public TakeoutOrderStatus getStatus() {
        return status;
    }

    public void setStatus(TakeoutOrderStatus status) {
        this.status = status;
    }

}
