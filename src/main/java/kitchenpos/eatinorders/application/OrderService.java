package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.dto.*;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.*;
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

    public OrderService(
            final EatInOrderRepository orderRepository,
            final EatInMenuRepository menuRepository,
            final EatInOrderTableRepository orderTableRepository
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
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
        EatInOrderTable orderTable = order.getOrderTable();
        // TODO: 질문
        // order.complete() 에서 변경한 상태가 commit 되지 않아서 아래 existsByOrderTableAndStatusNot 은 무조건 false 이지 않나요?
        // 아래 기능이 이 위치에 있는게 괜찮을까요?
        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.clear();
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

    private EatInOrderTable findOrderTableById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ORDER_TABLE));
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
                savedOrder.getType(),
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
