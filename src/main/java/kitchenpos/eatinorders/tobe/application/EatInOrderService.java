package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.ui.dto.EatInOrderRequest;
import kitchenpos.eatinorders.tobe.ui.dto.EatInOrderResponse;
import kitchenpos.eatinorders.tobe.ui.dto.OrderLineItemRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderService(
            final EatInOrderRepository eatInOrderRepository,
            final MenuRepository menuRepository,
            final OrderTableRepository orderTableRepository
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderRequest request) {
        List<OrderLineItemRequest> orderLineItemRequests = request.getOrderLineItemRequests();
        if (orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItemRequest::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }

        List<OrderLineItem> orderLineItems = orderLineItemRequests.stream()
                .map(it -> {
                    final Menu menu = menuRepository.findByIdAndDisplayedWithPrice(it.getMenuId(), it.getPrice())
                            .orElseThrow(NoSuchElementException::new);
                    return OrderLineItem.of(menu, it.getQuantity(), it.getPrice());
                }).collect(Collectors.toList());

        final OrderTable orderTable = orderTableRepository.findByIdNotOccupied(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);


        EatInOrder savedOrder = eatInOrderRepository.save(EatInOrder.of(orderLineItems, orderTable));
        return new EatInOrderResponse(
                savedOrder.getId(),
                savedOrder.getStatus(),
                savedOrder.getOrderDateTime(),
                savedOrder.getOrderLineItemSequence(),
                savedOrder.getOrderTableId()
        );
    }

    @Transactional
    public EatInOrderResponse accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findByIdAndStatus(orderId, EatInOrderStatus.WAITING)
                .orElseThrow(NoSuchElementException::new);

        eatInOrder.accepted();
        return new EatInOrderResponse(
                eatInOrder.getId(),
                eatInOrder.getStatus(),
                eatInOrder.getOrderDateTime(),
                eatInOrder.getOrderLineItemSequence(),
                eatInOrder.getOrderTableId()
        );
    }

    @Transactional
    public EatInOrderResponse serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findByIdAndStatus(orderId, EatInOrderStatus.ACCEPTED)
                .orElseThrow(NoSuchElementException::new);

        eatInOrder.serve();
        return new EatInOrderResponse(
                eatInOrder.getId(),
                eatInOrder.getStatus(),
                eatInOrder.getOrderDateTime(),
                eatInOrder.getOrderLineItemSequence(),
                eatInOrder.getOrderTableId()
        );
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

        return new EatInOrderResponse(
                eatInOrder.getId(),
                eatInOrder.getStatus(),
                eatInOrder.getOrderDateTime(),
                eatInOrder.getOrderLineItemSequence(),
                eatInOrder.getOrderTableId()
        );
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        return eatInOrderRepository.findAll().stream()
                .map(it -> new EatInOrderResponse(
                        it.getId(),
                        it.getStatus(),
                        it.getOrderDateTime(),
                        it.getOrderLineItemSequence(),
                        it.getOrderTableId()
                )).collect(Collectors.toList());
    }
}
