package kitchenpos.order.tobe.eatinorder.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.order.tobe.eatinorder.application.dto.CreateEatInOrderRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.DetailEatInOrderResponse;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItems;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus;
import kitchenpos.order.tobe.eatinorder.domain.service.MenuClient;
import kitchenpos.order.tobe.eatinorder.domain.service.EatInOrderCreatePolicy;
import kitchenpos.order.tobe.eatinorder.event.EatInOrderCompleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {

    private final EatInOrderRepository orderRepository;
    private final MenuClient menuClient;
    private final EatInOrderCreatePolicy eatInOrderCreatePolicy;
    private final ApplicationEventPublisher applicationEventPublisher;

    public EatInOrderService(final EatInOrderRepository orderRepository, final MenuClient menuClient, EatInOrderCreatePolicy eatInOrderCreatePolicy,
        ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.menuClient = menuClient;
        this.eatInOrderCreatePolicy = eatInOrderCreatePolicy;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public DetailEatInOrderResponse create(final CreateEatInOrderRequest request) {
        EatInOrder eatInOrder = EatInOrder.create(LocalDateTime.now(), EatInOrderLineItems.from(request.getOrderLineItems(), menuClient),
            request.getOrderTableId(), eatInOrderCreatePolicy);
        return DetailEatInOrderResponse.of(orderRepository.save(eatInOrder));
    }

    @Transactional
    public DetailEatInOrderResponse accept(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
        eatInOrder.accept();

        return DetailEatInOrderResponse.of(eatInOrder);
    }

    @Transactional
    public DetailEatInOrderResponse serve(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
        eatInOrder.serve();

        return DetailEatInOrderResponse.of(eatInOrder);
    }

    @Transactional
    public DetailEatInOrderResponse complete(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
        eatInOrder.complete();

        if (!orderRepository.existsByOrderTableIdAndStatusNot(eatInOrder.getOrderTableId(), EatInOrderStatus.COMPLETED)) {
            applicationEventPublisher.publishEvent(new EatInOrderCompleteEvent(eatInOrder.getOrderTableId()));
        }

        return DetailEatInOrderResponse.of(eatInOrder);
    }

    @Transactional(readOnly = true)
    public List<DetailEatInOrderResponse> findAll() {
        return orderRepository.findAll()
            .stream()
            .map(DetailEatInOrderResponse::of)
            .collect(Collectors.toList());
    }
}
