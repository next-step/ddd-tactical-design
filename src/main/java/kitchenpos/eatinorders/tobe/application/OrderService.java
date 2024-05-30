package kitchenpos.eatinorders.tobe.application;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.eatinorders.tobe.application.adapter.MenuServiceAdapter;
import kitchenpos.eatinorders.tobe.application.dto.OrderCreationRequest;
import kitchenpos.eatinorders.tobe.application.dto.OrderLineItemCreationRequest;
import kitchenpos.eatinorders.tobe.domain.KitchenridersClient;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.OrderType;
import kitchenpos.eatinorders.tobe.factory.OrderFactory;
import kitchenpos.eatinorders.tobe.factory.OrderFactoryProvider;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.order.domain.DeliveryOrder;

@Service
public abstract class OrderService {
    protected static final String ORDER_TABLE_NOT_FOUND_ERROR = "주문 테이블을 찾을 수 없습니다.";

    protected static final String WRONG_ORDER_TYPE_ERROR = "잘못된 주문 타입입니다.";

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
        Map<UUID, Menu> menus = menuServiceAdapter.findAllByIdIn(
            request.orderLineItemsRequest()
                .stream()
                .map(OrderLineItemCreationRequest::menuId)
                .toList()
        ).stream()
        .collect(Collectors.toMap(Menu::getId, Function.identity()));

        return OrderLineItems.fromRequests(request.orderLineItemsRequest(), menus);
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