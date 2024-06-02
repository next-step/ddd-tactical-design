package kitchenpos.orders.tobe.application;

import static kitchenpos.orders.tobe.domain.OrderType.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.orders.tobe.application.adapter.MenuServiceAdapter;
import kitchenpos.orders.tobe.application.dto.OrderCreationRequest;
import kitchenpos.orders.tobe.application.dto.OrderLineItemCreationRequest;
import kitchenpos.orders.tobe.domain.DeliveryOrder;
import kitchenpos.orders.tobe.domain.KitchenridersClient;
import kitchenpos.orders.tobe.domain.Order;
import kitchenpos.orders.tobe.domain.OrderLineItems;
import kitchenpos.orders.tobe.domain.OrderRepository;
import kitchenpos.orders.tobe.domain.OrderTableRepository;
import kitchenpos.orders.tobe.domain.OrderType;
import kitchenpos.orders.tobe.domain.factory.OrderFactoryProvider;

public abstract class OrderService {

    protected final OrderRepository orderRepository;
    protected final MenuServiceAdapter menuServiceAdapter;

    protected final OrderTableRepository orderTableRepository;

    protected final KitchenridersClient kitchenridersClient;
    protected final OrderFactoryProvider orderFactoryProvider;

    public OrderService(
        final OrderRepository orderRepository,
        final MenuServiceAdapter menuServiceAdapter,
        final OrderTableRepository orderTableRepository,
        final KitchenridersClient kitchenridersClient,
        final OrderFactoryProvider orderFactoryProvider
    ) {
        this.orderRepository = orderRepository;
        this.menuServiceAdapter = menuServiceAdapter;
        this.orderTableRepository = orderTableRepository;
        this.kitchenridersClient = kitchenridersClient;
        this.orderFactoryProvider = orderFactoryProvider;
    }

    public abstract Order create(final OrderCreationRequest request);

    protected OrderLineItems createOrderLineItems(OrderCreationRequest request) {
        Map<UUID, Menu> menus = menuServiceAdapter.findAllByIdIn(extractMenuIds(request))
        .stream()
        .collect(Collectors.toMap(Menu::getId, Function.identity()));

        return OrderLineItems.fromRequests(request.orderLineItemsRequest(), menus);
    }

    private static List<UUID> extractMenuIds(OrderCreationRequest request) {
        return request.orderLineItemsRequest()
            .stream()
            .map(OrderLineItemCreationRequest::menuId)
            .toList();
    }

    @Transactional
    public Order accept(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        return order.accepted(kitchenridersClient);
    }

    @Transactional
    public Order serve(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        return order.served();
    }

    @Transactional
    public Order startDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

        if (order.getType() != OrderType.DELIVERY) {
            throw new IllegalArgumentException(WRONG_ORDER_TYPE_ERROR);
        }

        return ((DeliveryOrder)order).delivering();
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

        if (order.getType() != OrderType.DELIVERY) {
            throw new IllegalArgumentException(WRONG_ORDER_TYPE_ERROR);
        }

        return ((DeliveryOrder)order).delivered();
    }

    public abstract Order complete(final UUID orderId);

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}