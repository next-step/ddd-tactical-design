package kitchenpos.eatinorders.tobe.domain.eatinorder.service;

import kitchenpos.eatinorders.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.Order;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.eatinorder.repository.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.eatinorder.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.menu.service.MenuService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static kitchenpos.eatinorders.model.OrderStatus.COMPLETION;

@Component
public class OrderService {

    private MenuService menuService;

    private OrderRepository orderRepository;

    private OrderTableRepository orderTableService;

    public OrderService(MenuService menuService, OrderRepository orderRepository, OrderTableRepository orderTableService) {
        this.menuService = menuService;
        this.orderRepository = orderRepository;
        this.orderTableService = orderTableService;
    }

    @Transactional
    public Order create(final Order order) {
        final List<OrderLineItem> orderLineItems = order.getOrderLineItems();

        if (CollectionUtils.isEmpty(orderLineItems)) {
            throw new IllegalArgumentException();
        }

        if (orderLineItems.size() != menuService.countByMenuId(order.getMenuIds())) {
            throw new IllegalArgumentException();
        }

        final OrderTable orderTable = orderTableService.findById(order.getOrderTableId())
                .orElseThrow(IllegalArgumentException::new);

        if (Objects.isNull(orderTable) || orderTable.isEmpty()) {
            throw new IllegalArgumentException();
        }

        order.initOrder(orderTable.getId());
        final Order savedOrder = orderRepository.save(order);

        savedOrder.updateOrderLineItems(orderLineItems);
        return savedOrder;
    }

    public List<Order> list() {
        return orderRepository.findAll();
    }

    public Order changeOrderStatus(final Long orderId, final Order order) {
        final Order savedOrder = orderRepository.findById(orderId)
                .orElseThrow(IllegalArgumentException::new);

        savedOrder.changeOrderStatus(order);
        return savedOrder;

    }

    public OrderStatus getOrderStatusByOrderTableId(Long orderTableId) {
        return orderRepository.findByOrderTableId(orderTableId).getOrderStatus();
    }

    public boolean existsByOrderTableIdInAndOrderStatus(List<Long> orderTableIds) {
        int count = orderRepository.countByOrderTableIdsAndNotEqaulOrderStatus(orderTableIds, COMPLETION);
        return count > 0;
    }
}
