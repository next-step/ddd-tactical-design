package kitchenpos.order.takeoutorder.application;

import static kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus.ACCEPTED;
import static kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus.COMPLETED;
import static kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus.SERVED;
import static kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus.WAITING;

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
import kitchenpos.order.takeoutorder.domain.TakeoutOrder;
import org.springframework.stereotype.Service;

@Service
public class DefaultTakeoutOrderService implements TakeoutOrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public DefaultTakeoutOrderService(OrderRepository orderRepository, MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
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
        TakeoutOrder order = new TakeoutOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(orderLineItems);
        return orderRepository.save(order);
    }

    @Override
    public Order accept(UUID orderId) {
        final TakeoutOrder order = (TakeoutOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != WAITING) {
            throw new IllegalStateException();
        }
        order.setStatus(ACCEPTED);
        return order;
    }

    @Override
    public Order serve(UUID orderId) {
        final TakeoutOrder order = (TakeoutOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(SERVED);
        return order;
    }

    @Override
    public Order complete(UUID orderId) {
        final TakeoutOrder order = (TakeoutOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final OrderType type = order.getType();

        if (type == OrderType.TAKEOUT) {
            if (order.getStatus() != SERVED) {
                throw new IllegalStateException();
            }
        }
        order.setStatus(COMPLETED);
        return order;
    }

}
