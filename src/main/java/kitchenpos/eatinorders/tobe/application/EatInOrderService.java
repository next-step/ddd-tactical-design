package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.EatInOrderRequest;
import kitchenpos.eatinorders.tobe.application.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderService(EatInOrderRepository eatInOrderRepository, MenuRepository menuRepository, OrderTableRepository orderTableRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public EatInOrder create(final EatInOrderRequest request) {
        request.validate();

        final List<OrderLineItem> orderLineItems = request.getOrderLineItems()
            .stream()
            .map(OrderLineItemRequest::toOrderLineItem)
            .collect(Collectors.toList());
        final List<Menu> menus = findMenus(orderLineItems);
        final OrderTable orderTable = findOrderTable(request.getOrderTableId());

        return EatInOrder.create(
            menus,
            orderLineItems,
            orderTable
        );
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = findOrder(orderId);
        order.accept();
        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = findOrder(orderId);
        order.serve();
        return order;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = findOrder(orderId);
        order.complete();
        final OrderTable orderTable = findOrderTable(order.getOrderTableId());
        if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.clear();
        }
        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }

    private EatInOrder findOrder(final UUID orderId) {
        return eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
    }

    private List<Menu> findMenus(final List<OrderLineItem> orderLineItems) {
        return menuRepository.findAllByIdIn(
            orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList())
        );
    }

    private OrderTable findOrderTable(final UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
    }
}
