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
import kitchenpos.order.tobe.eatinorder.domain.MenuClient;
import kitchenpos.order.tobe.eatinorder.domain.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository;
import kitchenpos.order.tobe.eatinorder.event.EatInOrderCompleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {

    private final EatInOrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    private final MenuClient menuClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    public EatInOrderService(final EatInOrderRepository orderRepository, final OrderTableRepository orderTableRepository,
        final MenuClient menuClient, ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuClient = menuClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public DetailEatInOrderResponse create(final CreateEatInOrderRequest request) {
        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId()).orElseThrow(NoSuchElementException::new);
        if (orderTable.isEmpty()) {
            throw new IllegalStateException("빈 테이블은 주문을 할 수 없습니다.");
        }

        EatInOrder eatInOrder = EatInOrder.create(LocalDateTime.now(), EatInOrderLineItems.from(request.getOrderLineItems(), menuClient), orderTable.getId());
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
