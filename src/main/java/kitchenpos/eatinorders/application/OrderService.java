package kitchenpos.eatinorders.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.eatinorders.application.dto.CreateOrderRequest;
import kitchenpos.eatinorders.application.dto.OrderLineItemDto;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderCompletePolicy;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.eatinorders.domain.OrderLineItems;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.Price;

@Service
public class OrderService {
    private final EatInOrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final EatInOrderCompletePolicy eatInOrderCompletePolicy;

    public OrderService(
            final EatInOrderRepository orderRepository,
            final MenuRepository menuRepository,
            final OrderTableRepository orderTableRepository,
            final EatInOrderCompletePolicy eatInOrderCompletePolicy) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.eatInOrderCompletePolicy = eatInOrderCompletePolicy;
    }

    @Transactional
    public EatInOrder create(final CreateOrderRequest request) {
        final List<OrderLineItemDto> orderLineItemRequests = request.getOrderLineItems();
        List<OrderLineItem> orderLineItems = orderLineItemRequests.stream()
                .map(it -> createOrderLineItem(it))
                .collect(Collectors.toList());
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItems.stream()
                        .map(OrderLineItem::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), new OrderLineItems(orderLineItems), orderTable);
        return orderRepository.save(eatInOrder);
    }

    private OrderLineItem createOrderLineItem(OrderLineItemDto it) {
        final Menu menu = menuRepository.findById(it.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
        if (menu.getPrice().isNotSame(new Price(it.getPrice()))) {
            throw new IllegalArgumentException();
        }
        return new OrderLineItem(it.getMenuId(), it.getQuantity(), it.getPrice());
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.accept();
        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.serve();
        return order;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.complete(eatInOrderCompletePolicy);
        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }
}
