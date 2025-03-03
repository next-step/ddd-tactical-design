package kitchenpos.order.takeout.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kitchenpos.order.common.domain.entity.Order;

@Entity
@DiscriminatorValue("TAKEOUT")
public class TakeOutOrder extends Order {

    protected TakeOutOrder() {}

}
