package kitchenpos.order.eatinorder.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.EatInOrderStatus;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        validateOrderLineItemMenuIsExists(orderLineItemRequests);

        final List<OrderLineItem> orderLineItems = createOrderLineItemsByRequest(orderLineItemRequests);
        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), LocalDateTime.now(),
                orderLineItems, EatInOrderFlow.WAITING);

        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.occupyOrderTable(orderTable);

        return eatInOrderRepository.save(eatInOrder);
    }

    private void validateOrderLineItemMenuIsExists(List<OrderLineItem> orderLineItemRequests) {
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItem::getMenuId)
                        .toList()
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
    }

    private List<OrderLineItem> createOrderLineItemsByRequest(List<OrderLineItem> orderLineItemRequests) {
        final List<OrderLineItem> orderLineItems = new ArrayList<>();
        for (final OrderLineItem itemRq : orderLineItemRequests) {
            final Menu menu = menuRepository.findById(itemRq.getMenuId())
                    .orElseThrow(NoSuchElementException::new);
            final OrderLineItem orderLineItem = new OrderLineItem(menu, itemRq.getQuantity(), menu.getId(),
                    itemRq.getPrice());
            orderLineItems.add(orderLineItem);
        }
        return orderLineItems;
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.validateOrderFlow(EatInOrderStatus.ACCEPTED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.validateOrderFlow(EatInOrderStatus.SERVED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.validateOrderFlow(EatInOrderStatus.COMPLETED);

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
