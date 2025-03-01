package kitchenpos.legacy.order.takeoutorder.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kitchenpos.legacy.order.common.model.Order;
import kitchenpos.legacy.order.common.model.OrderType;

@Entity
@DiscriminatorValue("TAKEOUT")
public class TakeOutOrder extends Order {

    @Override
    public OrderType getType() {
        return OrderType.TAKEOUT;
    }

}
