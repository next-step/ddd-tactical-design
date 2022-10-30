package kitchenpos.takeoutorders;

import kitchenpos.common.AbstractOrder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("TAKEOUT")
public class TakeOutOrder extends AbstractOrder {

    @Enumerated(EnumType.STRING)
    private TakeOutOrderStatus status;

}
