package kitchenpos.deliveryorders.domain;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import kitchenpos.common.DomainService;

@DomainService
public class DeliveryOrderRegisterListener {
    private final DeliveryOrderRepository orderRepository;

    public DeliveryOrderRegisterListener(DeliveryOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void register(UUID orderMasterId, BigDecimal totalOrderPrice, String deliveryAddress) {
        OrderMasterId delivery = new OrderMasterId(orderMasterId, totalOrderPrice);
        DeliveryAddress address = DeliveryAddress.of(deliveryAddress);
        DeliveryOrder deliveryOrder = new DeliveryOrder(delivery, address);
        orderRepository.save(deliveryOrder);
    }
}
