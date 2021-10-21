package kitchenpos.eatinorders.tobe.application;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.infra.OrderMenuAdaptor;
import kitchenpos.eatinorders.tobe.ui.OrderForm;
import kitchenpos.eatinorders.tobe.ui.OrderLineItemForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TobeOrderService {
    private final TobeOrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final OrderMenuAdaptor orderMenuAdaptor;
    private final OrderLineFactory orderLineFactory;
    private final KitchenridersClient kitchenridersClient;

    public TobeOrderService(
        final TobeOrderRepository orderRepository,
        final OrderTableRepository orderTableRepository,
        final OrderMenuAdaptor orderMenuAdaptor,
        final OrderLineFactory orderLineFactory,
        final KitchenridersClient kitchenridersClient
    ) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderMenuAdaptor = orderMenuAdaptor;
        this.orderLineFactory = orderLineFactory;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public OrderForm create(final OrderForm request) {
        ValidationOrderService.validateOrderType(request.getType());
        ValidationOrderService.validateOrderLineItem(request.getOrderLineItems());

        final List<OrderLineItemForm> orderLineItemRequests = request.getOrderLineItems();
        final List<Menu> menus = orderMenuAdaptor.menufindAllByIdIn(
            orderLineItemRequests.stream()
                .map(OrderLineItemForm::getMenuId)
                .collect(Collectors.toList())
        );

        ValidationOrderService.menuSizeEqualsOrderLineItemSize(menus, orderLineItemRequests);

        final TobeOrderLineItems orderLineItems = orderLineFactory.findOrderLineItems(request, menus);
        return OrderForm.of(new TobeOrder(
                request.getType(),
                orderLineItems,
                request.getDeliveryAddress(),
                request.getOrderTableId()
        ));
    }

//    @Transactional
//    public Order accept(final UUID orderId) {
//        final Order order = orderRepository.findById(orderId)
//                .orElseThrow(NoSuchElementException::new);
//        if (order.getStatus() != OrderStatus.WAITING) {
//            throw new IllegalStateException();
//        }
//        if (order.getType() == OrderType.DELIVERY) {
//            BigDecimal sum = BigDecimal.ZERO;
//            for (final OrderLineItem orderLineItem : order.getOrderLineItems()) {
//                sum = orderLineItem.getMenu()
//                        .getPrice()
//                        .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
//            }
//            kitchenridersClient.requestDelivery(orderId, sum, order.getDeliveryAddress());
//        }
//        order.setStatus(OrderStatus.ACCEPTED);
//        return order;
//    }
//
//    @Transactional
//    public Order serve(final UUID orderId) {
//        final Order order = orderRepository.findById(orderId)
//                .orElseThrow(NoSuchElementException::new);
//        if (order.getStatus() != OrderStatus.ACCEPTED) {
//            throw new IllegalStateException();
//        }
//        order.setStatus(OrderStatus.SERVED);
//        return order;
//    }
//
//    @Transactional
//    public Order startDelivery(final UUID orderId) {
//        final Order order = orderRepository.findById(orderId)
//                .orElseThrow(NoSuchElementException::new);
//        if (order.getType() != OrderType.DELIVERY) {
//            throw new IllegalStateException();
//        }
//        if (order.getStatus() != OrderStatus.SERVED) {
//            throw new IllegalStateException();
//        }
//        order.setStatus(OrderStatus.DELIVERING);
//        return order;
//    }
//
//    @Transactional
//    public Order completeDelivery(final UUID orderId) {
//        final Order order = orderRepository.findById(orderId)
//                .orElseThrow(NoSuchElementException::new);
//        if (order.getStatus() != OrderStatus.DELIVERING) {
//            throw new IllegalStateException();
//        }
//        order.setStatus(OrderStatus.DELIVERED);
//        return order;
//    }
//
//    @Transactional
//    public Order complete(final UUID orderId) {
//        final Order order = orderRepository.findById(orderId)
//                .orElseThrow(NoSuchElementException::new);
//        final OrderType type = order.getType();
//        final OrderStatus status = order.getStatus();
//        if (type == OrderType.DELIVERY) {
//            if (status != OrderStatus.DELIVERED) {
//                throw new IllegalStateException();
//            }
//        }
//        if (type == OrderType.TAKEOUT || type == OrderType.EAT_IN) {
//            if (status != OrderStatus.SERVED) {
//                throw new IllegalStateException();
//            }
//        }
//        order.setStatus(OrderStatus.COMPLETED);
//        if (type == OrderType.EAT_IN) {
//            final OrderTable orderTable = order.getOrderTable();
//            if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
//                orderTable.setNumberOfGuests(0);
//                orderTable.setEmpty(true);
//            }
//        }
//        return order;
//    }

    @Transactional(readOnly = true)
    public List<OrderForm> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderForm::of)
                .collect(Collectors.toList());
    }
}
