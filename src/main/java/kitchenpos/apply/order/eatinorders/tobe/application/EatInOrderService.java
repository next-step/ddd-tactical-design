package kitchenpos.apply.order.eatinorders.tobe.application;

import kitchenpos.apply.order.eatinorders.tobe.domain.*;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.EatInOrderResponse;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.EatInOrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final OrderTableRepository orderTableRepository;
    private final OrderLineItemsFactory orderLineItemsFactory;

    public EatInOrderService(
            final EatInOrderRepository eatInOrderRepository,
            final OrderTableRepository orderTableRepository,
            OrderLineItemsFactory orderLineItemsFactory
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderLineItemsFactory = orderLineItemsFactory;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderRequest request) {
        final OrderLineItems orderLineItems = orderLineItemsFactory.create(request.getOrderLineItemRequests());
        final OrderTable orderTable = orderTableRepository.findByIdNotOccupied(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        final EatInOrder savedOrder = eatInOrderRepository.save(EatInOrder.of(orderLineItems, orderTable));
        return new EatInOrderResponse(savedOrder);
    }

    @Transactional
    public EatInOrderResponse accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findByIdAndStatus(orderId, EatInOrderStatus.WAITING)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.accepted();
        return new EatInOrderResponse(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findByIdAndStatus(orderId, EatInOrderStatus.ACCEPTED)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.serve();
        return new EatInOrderResponse(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findByIdAndStatus(orderId, EatInOrderStatus.SERVED)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.complete();
        final OrderTable orderTable = eatInOrder.getOrderTable();

        if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
            orderTable.clear();
        }

        return new EatInOrderResponse(eatInOrder);
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        return eatInOrderRepository.findAll().stream()
                .map(EatInOrderResponse::new)
                .collect(Collectors.toList());
    }
}
