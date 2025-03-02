package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.out.LoadEatInOrderPort;
import kitchenpos.eatinorder.application.port.out.MenuEatInOrderLineItemMapper;
import kitchenpos.eatinorder.application.port.out.SaveEatInOrderPort;
import kitchenpos.eatinorder.application.service.model.CreateEatInOrderRequest;
import kitchenpos.eatinorder.domain.model.todo.EatInOrder;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;
import kitchenpos.eatinorder.domain.policy.CreateEatInOrderPolicy;
import kitchenpos.shared.event.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final LoadEatInOrderPort loadEatInOrderPort;
    private final SaveEatInOrderPort saveEatInOrderPort;
    private final MenuEatInOrderLineItemMapper menuEatInOrderLineItemMapper;
    private final CreateEatInOrderPolicy createEatInOrderPolicy;
    private final ApplicationEventPublisher eventPublisher;

    public EatInOrderService(
            final LoadEatInOrderPort loadEatInOrderPort,
            final SaveEatInOrderPort saveEatInOrderPort,
            final MenuEatInOrderLineItemMapper menuEatInOrderLineItemMapper,
            final CreateEatInOrderPolicy createEatInOrderPolicy, ApplicationEventPublisher eventPublisher
    ) {
        this.loadEatInOrderPort = loadEatInOrderPort;
        this.saveEatInOrderPort = saveEatInOrderPort;
        this.menuEatInOrderLineItemMapper = menuEatInOrderLineItemMapper;
        this.createEatInOrderPolicy = createEatInOrderPolicy;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public EatInOrder create(final CreateEatInOrderRequest request) {
        List<EatInOrderLineItem> eatInOrderLineItems = menuEatInOrderLineItemMapper.toEatInOrderLines(request.orderLineItemRequests());
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), LocalDateTime.now(), eatInOrderLineItems, request.orderTableId(), createEatInOrderPolicy);
        return saveEatInOrderPort.save(eatInOrder);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = findById(orderId);
        eatInOrder.accept();
        return saveEatInOrderPort.save(eatInOrder);
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder eatInOrder = findById(orderId);
        eatInOrder.serve();
        return saveEatInOrderPort.save(eatInOrder);
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = findById(orderId);
        eatInOrder.complete();
        publishEvent(eatInOrder);
        return saveEatInOrderPort.save(eatInOrder);
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return loadEatInOrderPort.findAll();
    }

    @Transactional(readOnly = true)
    public EatInOrder findById(UUID orderId) {
        return loadEatInOrderPort.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }

    private void publishEvent(EatInOrder eatInOrder) {
        List<DomainEvent> domainEvents = eatInOrder.getDomainEvents();
        domainEvents.forEach(eventPublisher::publishEvent);
        eatInOrder.clearDomainEvents();
    }
}
