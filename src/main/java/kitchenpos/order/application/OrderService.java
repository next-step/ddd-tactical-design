package kitchenpos.order.application;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.application.dto.OrderCreationRequest;
import kitchenpos.order.application.dto.OrderLineItemCreationRequest;
import kitchenpos.order.domain.KitchenridersClient;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItems;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderTableRepository;
import kitchenpos.order.domain.OrderType;
import kitchenpos.order.factory.OrderFactory;
import kitchenpos.order.factory.OrderFactoryProvider;

@Service
public class OrderService {
    private static final String ORDER_TABLE_NOT_FOUND_ERROR = "주문 테이블을 찾을 수 없습니다.";

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final KitchenridersClient kitchenridersClient;
    private final OrderFactoryProvider orderFactoryProvider;

    public OrderService(
        final OrderRepository orderRepository,
        final MenuRepository menuRepository,
        final OrderTableRepository orderTableRepository,
        final KitchenridersClient kitchenridersClient,
        final OrderFactoryProvider orderFactoryProvider
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.kitchenridersClient = kitchenridersClient;
        this.orderFactoryProvider = orderFactoryProvider;
    }

    @Transactional
    public Order create(final OrderCreationRequest request) {
        Map<UUID, Menu> menus = menuRepository.findAllByIdIn(
            request.orderLineItemsRequest()
                .stream()
                .map(OrderLineItemCreationRequest::menuId)
                .toList()
        ).stream()
        .collect(Collectors.toMap(Menu::getId, Function.identity()));

        OrderLineItems orderLineItems = OrderLineItems.fromRequests(request.orderLineItemsRequest(), menus);

        OrderTable orderTable = null;
        if (request.type() == OrderType.EAT_IN) {
            orderTable = orderTableRepository.findById(request.orderTableId())
                .orElseThrow(() -> new NoSuchElementException(ORDER_TABLE_NOT_FOUND_ERROR));

            if (!orderTable.isOccupied()) {
                throw new IllegalStateException();
            }
        }

        OrderFactory orderFactory = orderFactoryProvider.getFactory(request.type());
        Order order = orderFactory.createOrder(orderLineItems, orderTable, request.deliveryAddress());

        return orderRepository.save(order);
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
        return order.delivering();
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        return order.delivered();
    }

    @Transactional
    public Order complete(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        return order.completed(orderRepository);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}