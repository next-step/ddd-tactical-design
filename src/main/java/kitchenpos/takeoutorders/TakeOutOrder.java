package kitchenpos.takeoutorders;

import kitchenpos.common.Order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("TAKEOUT")
public class TakeOutOrder extends Order {

    @Enumerated(EnumType.STRING)
    private TakeOutOrderStatus status;

}
