package kitchenpos.eatinorders.application.orders;

import kitchenpos.eatinorders.application.orders.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.domain.orders.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuClient menuClient;
    private final EatInOrderPolicy eatInOrderPolicy;

    public EatInOrderService(
            final EatInOrderRepository eatInOrderRepository,
            final MenuClient menuClient,
            final EatInOrderPolicy eatInOrderPolicy
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuClient = menuClient;
        this.eatInOrderPolicy = eatInOrderPolicy;
    }

    @Transactional
    public EatInOrder create(final EatInOrderCreateRequest request) {
        final List<EatInOrderLineItemMaterial> orderLineItemMaterials = request.getOrderLineItems();
        final EatInOrderLineItems orderLineItems = EatInOrderLineItems.from(orderLineItemMaterials, menuClient);
        EatInOrder eatInOrder = EatInOrder.waitingOrder(
                UUID.randomUUID(),
                LocalDateTime.now(),
                orderLineItems,
                request.getOrderTableId(),
                eatInOrderPolicy
        );
        return eatInOrderRepository.save(eatInOrder);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.accept();
        return eatInOrder;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.served();
        return eatInOrder;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.complete(eatInOrderPolicy);
        return eatInOrder;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }
}
