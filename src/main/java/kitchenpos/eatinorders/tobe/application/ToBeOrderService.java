package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ToBeOrderService {
    private final ToBeOrderRepository orderRepository;
    private final ToBeMenuRepository menuRepository;
    private final ToBeOrderTableRepository orderTableRepository;

    public ToBeOrderService(
        final ToBeOrderRepository orderRepository,
        final ToBeMenuRepository menuRepository,
        final ToBeOrderTableRepository orderTableRepository
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public ToBeOrder create(final ToBeOrder request) {
        final List<ToBeOrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<ToBeMenu> menus = menuRepository.findAllByIdIn(
            orderLineItemRequests.stream()
                .map(ToBeOrderLineItem::getMenuId)
                .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<ToBeOrderLineItem> orderLineItems = new ArrayList<>();
        for (final ToBeOrderLineItem orderLineItemRequest : orderLineItemRequests) {
            final long quantity = orderLineItemRequest.getQuantity();

            final ToBeMenu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
                .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final ToBeOrderLineItem orderLineItem = new ToBeOrderLineItem(menu,quantity);
            orderLineItems.add(orderLineItem);
        }
        ToBeOrder order = new ToBeOrder(ToBeOrderStatus.WAITING, LocalDateTime.now(), orderLineItems);

        final ToBeOrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
            .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        order.setOrderTable(orderTable);

        return orderRepository.save(order);
    }

    @Transactional
    public ToBeOrder accept(final UUID orderId) {
        final ToBeOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != ToBeOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.changeStatus(ToBeOrderStatus.ACCEPTED);
        return order;
    }

    @Transactional
    public ToBeOrder serve(final UUID orderId) {
        final ToBeOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != ToBeOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.changeStatus(ToBeOrderStatus.SERVED);
        return order;
    }

    @Transactional
    public ToBeOrder complete(final UUID orderId) {
        final ToBeOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final ToBeOrderStatus status = order.getStatus();
        if (status != ToBeOrderStatus.SERVED) {
            throw new IllegalStateException();
        }

        order.changeStatus(ToBeOrderStatus.COMPLETED);
        final ToBeOrderTable orderTable = order.getOrderTable();
        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, ToBeOrderStatus.COMPLETED)) {
            orderTable.zeroizeNumberOfGuests(new NumberOfGuests(0));
            orderTable.changeOccupied(false);
        }
        return order;
    }

    @Transactional(readOnly = true)
    public List<ToBeOrder> findAll() {
        return orderRepository.findAll();
    }
}
