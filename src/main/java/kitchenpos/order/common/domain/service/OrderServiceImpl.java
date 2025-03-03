package kitchenpos.order.common.domain.service;

import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.domain.repository.OrderRepository;
import kitchenpos.order.common.domain.repository.OrderTableRepository;
import kitchenpos.order.delivery.domain.service.DeliveryKitchenridersClient;
import org.springframework.stereotype.Service;

// TODO: 바운디드 컨텍스트 따라 분리해야함.
@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final DeliveryKitchenridersClient deliveryKitchenridersClient;

    public OrderServiceImpl(
        final OrderRepository orderRepository,
        final MenuRepository menuRepository,
        final OrderTableRepository orderTableRepository,
        final DeliveryKitchenridersClient deliveryKitchenridersClient
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.deliveryKitchenridersClient = deliveryKitchenridersClient;
    }

//    @Transactional
//    public OrderResponse.GetOrder create(final Order request) {
//        final OrderType type = request.getType();
//        if (Objects.isNull(type)) {
//            throw new IllegalArgumentException();
//        }
//        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
//        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
//            throw new IllegalArgumentException();
//        }
//        final List<Menu> menus = menuRepository.findAllByMenuIdIn(
//            orderLineItemRequests.stream()
//                .map(OrderLineItem::getMenuId)
//                .toList()
//        );
//        if (menus.size() != orderLineItemRequests.size()) {
//            throw new IllegalArgumentException();
//        }
//        final List<OrderLineItem> orderLineItems = new ArrayList<>();
//        for (final OrderLineItem orderLineItemRequest : orderLineItemRequests) {
//            final long quantity = orderLineItemRequest.getQuantity();
//            if (type != OrderType.EAT_IN) {
//                if (quantity < 0) {
//                    throw new IllegalArgumentException();
//                }
//            }
//            final Menu menu = menuRepository.findByMenuId(orderLineItemRequest.getMenuId())
//                .orElseThrow(NoSuchElementException::new);
//            if (!menu.isDisplayed()) {
//                throw new IllegalStateException();
//            }
//
//            if (!menu.isPriceEqual(orderLineItemRequest.getPrice())) {
//                throw new IllegalArgumentException();
//            }
//            final OrderLineItem orderLineItem = new OrderLineItem();
//            orderLineItem.setMenuId(menu.getMenuId());
//            orderLineItem.setQuantity(quantity);
//            orderLineItems.add(orderLineItem);
//        }
//        Order order = new Order();
//        order.setId(UUID.randomUUID());
//        order.setType(type);
//        order.setStatus(OrderStatus.WAITING);
//        order.setOrderDateTime(LocalDateTime.now());
//        order.setOrderLineItems(orderLineItems);
//        if (type == OrderType.DELIVERY) {
//            final String deliveryAddress = request.getDeliveryAddress();
//            if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
//                throw new IllegalArgumentException();
//            }
//            order.setDeliveryAddress(deliveryAddress);
//        }
//        if (type == OrderType.EAT_IN) {
//            final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
//                .orElseThrow(NoSuchElementException::new);
//            if (!orderTable.isOccupied()) {
//                throw new IllegalStateException();
//            }
//            order.setOrderTable(orderTable);
//        }
//        return orderRepository.save(order);
//    }
//
//    @Transactional
//    public OrderResponse.GetOrder accept(final UUID orderId) {
//        final Order order = orderRepository.findById(orderId)
//            .orElseThrow(NoSuchElementException::new);
//        if (order.getStatus() != OrderStatus.WAITING) {
//            throw new IllegalStateException();
//        }
//        if (order.getType() == OrderType.DELIVERY) {
//            BigDecimal sum = BigDecimal.ZERO;
//
//            // TODO: step3 보완
////            for (final OrderLineItem orderLineItem : order.getOrderLineItems()) {
////                sum = orderLineItem.getMenu()
////                    .getPrice()
////                    .price()
////                    .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
////            }
////            deliveryKitchenridersClient.requestDelivery(orderId, sum, order.getDeliveryAddress());
//        }
//        order.setStatus(OrderStatus.ACCEPTED);
//        return order;
//    }
//
//
//    @Transactional
//    public OrderResponse.GetOrder complete(final UUID orderId) {
//        final Order order = orderRepository.findById(orderId)
//            .orElseThrow(NoSuchElementException::new);
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
//            if (!orderRepository.existsByOrderTableAndStatusNot(orderTable,
//                OrderStatus.COMPLETED)) {
//                orderTable.setNumberOfGuests(0);
//                orderTable.setOccupied(false);
//            }
//        }
//        return order;
//    }


}
