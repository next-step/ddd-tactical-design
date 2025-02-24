package kitchenpos.order.eatinorder.application;

import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.ACCEPTED;
import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.COMPLETED;
import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.SERVED;
import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.WAITING;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderLineItem;
import kitchenpos.order.common.domain.OrderRepository;
import kitchenpos.order.common.domain.OrderType;
import kitchenpos.order.eatinorder.domain.EatInOrder;
import kitchenpos.order.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.eatinorder.domain.OrderTable;
import kitchenpos.order.eatinorder.domain.OrderTableRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultEatInOrderService implements EatInOrderService {

    private final OrderRepository orderRepository;
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;

    public DefaultEatInOrderService(OrderRepository orderRepository, EatInOrderRepository eatInOrderRepository,
        MenuRepository menuRepository, OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public Order create(Order request) {
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
            if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setMenu(menu);
            orderLineItem.setQuantity(quantity);
            orderLineItems.add(orderLineItem);
        }
        EatInOrder order = new EatInOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(orderLineItems);
        if (request instanceof EatInOrder eatInOrderRequest) {
            final OrderTable orderTable = orderTableRepository.findById(eatInOrderRequest.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
            if (!orderTable.isOccupied()) {
                throw new IllegalStateException();
            }
            order.setOrderTable(orderTable);
        }
        return orderRepository.save(order);
    }

    @Override
    public Order accept(UUID orderId) {
        final EatInOrder order = (EatInOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != WAITING) {
            throw new IllegalStateException();
        }
        order.setStatus(ACCEPTED);
        return order;
    }

    @Override
    public Order serve(UUID orderId) {
        final EatInOrder order = (EatInOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(SERVED);
        return order;
    }

    @Override
    public Order complete(UUID orderId) {
        final EatInOrder order = (EatInOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final OrderType type = order.getType();
        if (type == OrderType.EAT_IN) {
            if (order.getStatus() != SERVED) {
                throw new IllegalStateException();
            }
        }
        order.setStatus(COMPLETED);
        if (order instanceof EatInOrder eatInOrder) {
            final OrderTable orderTable = eatInOrder.getOrderTable();
            if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, COMPLETED)) {
                orderTable.setNumberOfGuests(0);
                orderTable.setOccupied(false);
            }
        }
        return order;
    }

}
