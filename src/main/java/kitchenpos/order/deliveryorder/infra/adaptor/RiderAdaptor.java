package kitchenpos.order.deliveryorder.infra.adaptor;

import java.math.BigDecimal;
import kitchenpos.order.deliveryorder.domain.model.DeliveryOrder;
import kitchenpos.order.deliveryorder.domain.port.RiderPort;
import kitchenpos.order.deliveryorder.infra.external.KitchenridersClient;

public class RiderAdaptor implements RiderPort {

    private KitchenridersClient kitchenridersClient;

    @Override
    public void requestRider(DeliveryOrder order) {
        kitchenridersClient.requestDelivery(order.getId(), BigDecimal.ONE, order.getDeliveryAddress());
    }
}
