package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.dto.*;
import kitchenpos.eatinorders.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.*;

@Service
public class OrderService {
    private final EatInOrderRepository orderRepository;
    private final EatInMenuRepository menuRepository;
    private final EatInOrderTableRepository orderTableRepository;
    private final OrderEventService orderEventService;

    public OrderService(
            final EatInOrderRepository orderRepository,
            final EatInMenuRepository menuRepository,
            final EatInOrderTableRepository orderTableRepository,
            final OrderEventService orderEventService
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderEventService = orderEventService;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderRequest request) {
        List<EatInOrderLineItemRequest> orderLineItemsRequest = request.getOrderLineItems();
        validateExistOrderLineRequest(orderLineItemsRequest);

        List<EatInMenu> eatInMenus = getEatInMenuAllByMenuIdIn(orderLineItemsRequest);
        validateExistMenu(eatInMenus, orderLineItemsRequest);

        EatInOrder eatInOrder = EatInOrder.create(
                UUID.randomUUID(),
                OrderStatus.WAITING,
                LocalDateTime.now(),
                EatInOrderLineItems.create(createEatInOrderLineItems(orderLineItemsRequest, eatInMenus)),
                findOrderTableById(request.getOrderTableId())
        );
        EatInOrder savedOrder = orderRepository.save(eatInOrder);
        return createEatInOrderResponse(savedOrder);
    }

    @Transactional
    public OrderStatusResponse accept(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        order.accept();
        return OrderStatusResponse.create(order.getId(), order.getStatus());
    }

    @Transactional
    public OrderStatusResponse serve(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        order.serve();
        return OrderStatusResponse.create(order.getId(), order.getStatus());
    }

    @Transactional
    public OrderStatusResponse complete(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        order.complete();
        if (!orderRepository.existsByOrderTableIdAndStatusNot(order.getOrderTableId(), OrderStatus.COMPLETED)) {
            orderEventService.notifyOrderComplete(OrderCompleteEvent.create(order.getOrderTableId()));
        }
        return OrderStatusResponse.create(order.getId(), order.getStatus());
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        List<EatInOrder> eatInOrders = orderRepository.findAll();
        return eatInOrders.stream()
                .map(this::createEatInOrderResponse)
                .collect(Collectors.toList());
    }

    private void validateExistMenu(List<EatInMenu> eatInMenus, List<EatInOrderLineItemRequest> orderLineItemsRequest) {
        if (eatInMenus.size() != orderLineItemsRequest.size()) {
            throw new IllegalArgumentException(NOT_FOUND_MENU);
        }
    }

    private EatInOrder getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    }

    private UUID findOrderTableById(UUID orderTableId) {
        EatInOrderTable eatInOrderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ORDER_TABLE));
        if (eatInOrderTable.isEmpty()) {
            throw new IllegalStateException(NOT_OCCUPIED_ORDER_TABLE);
        }
        return eatInOrderTable.getId();
    }

    private List<EatInOrderLineItem> createEatInOrderLineItems(List<EatInOrderLineItemRequest> orderLineItemRequests, List<EatInMenu> eatInMenus) {
        return orderLineItemRequests.stream()
                .map(request -> EatInOrderLineItem.create(findById(eatInMenus, request.getMenuId()), request.getQuantity(), Price.of(request.getPrice())))
                .collect(Collectors.toList());
    }

    private EatInMenu findById(List<EatInMenu> eatInMenus, UUID menuId) {
        return eatInMenus.stream()
                .filter(p -> menuId.equals(p.getId()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_MENU));

    }

    private EatInOrderResponse createEatInOrderResponse(EatInOrder savedOrder) {
        return EatInOrderResponse.create(
                savedOrder.getId(),
                savedOrder.getStatus(),
                savedOrder.getOrderDateTime(),
                savedOrder.getOrderTableId(),
                createEatInOrderLineItemResponse(savedOrder.getOrderLineItems())
        );
    }

    private List<EatInOrderLineItemResponse> createEatInOrderLineItemResponse(EatInOrderLineItems orderLineItems) {
        return orderLineItems.getOrderLineItems()
                .stream()
                .map(m -> EatInOrderLineItemResponse.create(m.getSeq(), m.getMenuId(), m.getQuantityValue()))
                .collect(Collectors.toList());
    }

    private List<EatInMenu> getEatInMenuAllByMenuIdIn(List<EatInOrderLineItemRequest> orderLineItemsRequest) {
        List<UUID> menuIds = orderLineItemsRequest.stream()
                .map(EatInOrderLineItemRequest::getMenuId)
                .collect(Collectors.toList());
        return menuRepository.findAllByIdIn(menuIds);
    }

    private void validateExistOrderLineRequest(List<EatInOrderLineItemRequest> orderLineItemsRequest) {
        if (ObjectUtils.isEmpty(orderLineItemsRequest)) {
            throw new IllegalArgumentException(ORDER_LINE_ITEM_EMPTY);
        }
    }
}
