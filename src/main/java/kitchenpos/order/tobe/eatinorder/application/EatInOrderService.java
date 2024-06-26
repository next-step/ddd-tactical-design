package kitchenpos.order.tobe.eatinorder.application;

import kitchenpos.common.event.publisher.OrderTableClearEvent;
import kitchenpos.order.tobe.eatinorder.application.dto.request.EatInOrderCreateRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.response.EatInOrderLineItemResponse;
import kitchenpos.order.tobe.eatinorder.application.dto.response.EatInOrderResponse;
import kitchenpos.order.tobe.eatinorder.application.dto.response.OrderTableResponse;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItem;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.tobe.eatinorder.domain.OrderLineItems;
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableRepository;
import kitchenpos.order.tobe.eatinorder.domain.validate.MenuValidator;
import org.springframework.context.ApplicationEventPublisher;
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
    private final MenuValidator menuValidator;
    private final ApplicationEventPublisher eventPublisher;

    public EatInOrderService(
            final EatInOrderRepository eatInOrderRepository,
            final OrderTableRepository orderTableRepository,
            final MenuValidator menuValidator,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuValidator = menuValidator;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderCreateRequest request) {
        final List<EatInOrderLineItem> orderLineItems = request.orderLineItems().stream()
                .map(dto -> new EatInOrderLineItem(dto.menuId(), dto.quantity(), dto.price()))
                .toList();

        OrderLineItems validatedOrderLineItems = new OrderLineItems(orderLineItems, menuValidator);
        OrderTable orderTable = findOrderTableById(request.orderTableId());

        EatInOrder order = new EatInOrder(UUID.randomUUID(), validatedOrderLineItems, orderTable);
        return toEatInOrderResponse(eatInOrderRepository.save(order));
    }

    @Transactional
    public EatInOrderResponse accept(final UUID orderId) {
        EatInOrder order = findOrderById(orderId);

        order.accept();

        return toEatInOrderResponse(order);
    }

    @Transactional
    public EatInOrderResponse serve(final UUID orderId) {
        EatInOrder order = findOrderById(orderId);

        order.serve();

        return toEatInOrderResponse(order);
    }

    @Transactional
    public EatInOrderResponse complete(final UUID orderId) {
        EatInOrder order = findOrderById(orderId);

        order.complete();

        eventPublisher.publishEvent(new OrderTableClearEvent(order.getOrderTable().getId()));

        return toEatInOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        return eatInOrderRepository.findAll().stream()
                .map(this::toEatInOrderResponse)
                .toList();
    }

    private OrderTable findOrderTableById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }

    private EatInOrder findOrderById(UUID orderId) {
        return eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }

    private EatInOrderResponse toEatInOrderResponse(EatInOrder order) {
        List<EatInOrderLineItemResponse> orderLineItemResponses = order.getOrderLineItems().getOrderLineItems().stream()
                .map(lineItem -> new EatInOrderLineItemResponse(
                        lineItem.getSeq(),
                        lineItem.getMenuId(),
                        lineItem.getQuantity(),
                        lineItem.getPrice()
                ))
                .collect(Collectors.toList());

        OrderTable orderTable = order.getOrderTable();
        OrderTableResponse orderTableResponse = new OrderTableResponse(
                orderTable.getId(),
                orderTable.getName(),
                orderTable.getNumberOfGuests(),
                orderTable.isOccupied()
        );

        return new EatInOrderResponse(
                order.getId(),
                order.getOrderTable().getId(),
                orderLineItemResponses,
                order.getStatus().name(),
                order.getOrderDateTime(),
                orderTableResponse
        );
    }
}
