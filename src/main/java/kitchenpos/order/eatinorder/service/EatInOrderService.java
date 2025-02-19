package kitchenpos.order.eatinorder.service;

import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.common.model.OrderType;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderStatus;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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
    public EatInOrder create(final EatInOrder request) {
        final OrderType type = request.getType();
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItem::getMenuId)
                        .toList()
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<OrderLineItem> orderLineItems = new ArrayList<>();
        for (final OrderLineItem orderLineItemRequest : orderLineItemRequests) {
            final long quantity = orderLineItemRequest.getQuantity();
            if (type != OrderType.EAT_IN) {
                if (quantity < 0) {
                    throw new IllegalArgumentException();
                }
            }
            final Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
                    .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getInnerPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setMenu(menu);
            orderLineItem.setQuantity(quantity);
            orderLineItems.add(orderLineItem);
        }
        EatInOrder eatInOrder = new EatInOrder();
        eatInOrder.setId(UUID.randomUUID());
        eatInOrder.setType(type);
        eatInOrder.setStatus(EatInOrderStatus.WAITING);
        eatInOrder.setOrderDateTime(LocalDateTime.now());
        eatInOrder.setOrderLineItems(orderLineItems);
        if (type == OrderType.EAT_IN) {
            final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                    .orElseThrow(NoSuchElementException::new);
            if (!orderTable.isOccupied()) {
                throw new IllegalStateException();
            }
            eatInOrder.setOrderTable(orderTable);
        }
        return eatInOrderRepository.save(eatInOrder);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getStatus() != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        eatInOrder.setStatus(EatInOrderStatus.ACCEPTED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getStatus() != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        eatInOrder.setStatus(EatInOrderStatus.SERVED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        final OrderType type = eatInOrder.getType();
        final EatInOrderStatus status = eatInOrder.getStatus();
        if (status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }

        eatInOrder.setStatus(EatInOrderStatus.COMPLETED);

        final OrderTable orderTable = eatInOrder.getOrderTable();
        if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
            orderTable.releaseTable();
        }

        return eatInOrder;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }
}
