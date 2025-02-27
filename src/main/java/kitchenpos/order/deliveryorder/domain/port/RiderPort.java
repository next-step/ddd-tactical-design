package kitchenpos.order.deliveryorder.domain.port;

import kitchenpos.order.deliveryorder.domain.model.DeliveryOrder;
import org.springframework.stereotype.Component;

@Component
public interface RiderPort {

    void requestRider(DeliveryOrder order);
}
