package kitchenpos.eatinorder.application;

import kitchenpos.eatinorder.tobe.domain.*;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderService(
            final OrderRepository orderRepository,
            final MenuRepository menuRepository,
            final OrderTableRepository orderTableRepository
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
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

        EatInOrder order = EatInOrder.of(LocalDateTime.now(), items, orderTable.getId());

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

        OrderTable orderTable = orderTableRepository.findById(order.getOrderTableId()).orElseThrow();
        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, order.getStatus())) {
//            orderTable.setNumberOfGuests(0);
//            orderTable.setOccupied(false);
            // todo table의 제어를 eat in order가 해야할 것으로 보이는데, 그치만 order table service에서 처리해야할 기능을 order에서 하는 것도 이상하다.
        }
        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }
}
