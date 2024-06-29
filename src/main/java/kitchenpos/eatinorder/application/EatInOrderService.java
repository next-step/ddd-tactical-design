package kitchenpos.eatinorder.application;

import kitchenpos.eatinorder.tobe.domain.order.*;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final EatInOrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final ClearedTable clearedTable;

    public EatInOrderService(
            final EatInOrderRepository orderRepository,
            final MenuRepository menuRepository,
            final OrderTableRepository orderTableRepository,
            final ClearedTable clearedTable
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.clearedTable = clearedTable;
    }

    @Transactional
    public EatInOrder create(final EatInOrder request) {
        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();

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
            if (!menu.isMenuDisplayStatus()) {
                throw new IllegalStateException();
            }
            if (menu.getMenuPrice().compareTo(orderLineItemRequest.getPrice().longValue()) != 0) {
                throw new IllegalArgumentException();
            }

            final var orderLineItem = OrderLineItem.of(quantity, menu.getId(), BigDecimal.valueOf(menu.getMenuPrice()));
            orderLineItems.add(orderLineItem);
        }
        final var items = new OrderLineItems(orderLineItems);

        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }

        EatInOrder order = EatInOrder.create(LocalDateTime.now(), items, orderTable.getId());

        return orderRepository.save(order);
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

        order.complete();
        OrderTable orderTable = orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);

        orderTable.cleared(clearedTable);

        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }
}
