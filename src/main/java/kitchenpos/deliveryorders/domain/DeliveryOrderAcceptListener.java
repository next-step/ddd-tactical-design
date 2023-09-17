package kitchenpos.deliveryorders.domain;

import java.util.UUID;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import kitchenpos.common.DomainService;
import kitchenpos.deliveryorders.infra.KitchenridersClient;

@DomainService
public class DeliveryOrderAcceptListener {
    private final DeliveryOrderRepository orderRepository;
    private final KitchenridersClient kitchenridersClient;

    public DeliveryOrderAcceptListener(DeliveryOrderRepository orderRepository,
        KitchenridersClient kitchenridersClient) {
        this.orderRepository = orderRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void accept(UUID orderMasterId) {
        DeliveryOrder deliveryOrder = orderRepository.findByOrderMasterId(orderMasterId)
            .orElseThrow(() -> new IllegalArgumentException("배달 주문 정보를 찾을 수 없습니다."));
        kitchenridersClient.requestDelivery(deliveryOrder.getId(),
            deliveryOrder.getTotalOrderPrice(),
            deliveryOrder.getDeliveryAddress().getValue());
    }
}
