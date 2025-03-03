package kitchenpos.order.eatin.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.eatin.domain.entity.OrderTable;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_table_id", insertable = false, updatable = false)
    private OrderTable orderTable;

    protected EatInOrder() {}

}
