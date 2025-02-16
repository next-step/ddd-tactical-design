package kitchenpos.order.deliveryorder.domain.service;

import kitchenpos.order.deliveryorder.domain.model.DeliveryOrder;
import kitchenpos.order.deliveryorder.domain.model.DeliveryOrderStatus;
import kitchenpos.order.deliveryorder.domain.port.RiderPort;

public class DeliveryOrderDomainService {

    private final RiderPort riderPort;
    private final DeliveryOrder deliveryOrder;

    public DeliveryOrderDomainService(RiderPort riderPort, DeliveryOrder deliveryOrder) {
        this.riderPort = riderPort;
        this.deliveryOrder = deliveryOrder;
    }

    public void doRide(DeliveryOrderStatus orderStatus) {
        boolean existsNextStep = deliveryOrder.validateOrderFlowAndFindNextStep(orderStatus);
        if (existsNextStep) {
            riderPort.requestRider(deliveryOrder);
        }
    }
}
