package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.OrderLineItems;
import kitchenpos.eatinorders.application.dto.OrderRequest;
import kitchenpos.eatinorders.application.dto.OrderResponse;
import kitchenpos.eatinorders.domain.order.MenuClient;
import kitchenpos.eatinorders.domain.order.Order;
import kitchenpos.eatinorders.domain.order.OrderRepository;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.domain.ordertable.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final MenuClient menuClient;

    public OrderService(
            final OrderRepository orderRepository,
            final OrderTableRepository orderTableRepository,
            final MenuClient menuClient
    ) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuClient = menuClient;
    }

    @Transactional
    public OrderResponse create(final OrderRequest request) {
        final OrderLineItems orderLineItems = request.getOrderLineItems();
        menuClient.validateOrderLineItems(orderLineItems);
        final OrderTable orderTable = getOrderTable(request.getOrderTableId());
        final Order order = new Order(orderLineItems, orderTable);
        return new OrderResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse accept(final UUID orderId) {
        final Order order = getOrder(orderId);
        order.accept();
        return new OrderResponse(order);
    }

    @Transactional
    public OrderResponse serve(final UUID orderId) {
        final Order order = getOrder(orderId);
        order.served();
        return new OrderResponse(order);
    }

    @Transactional
    public OrderResponse complete(final UUID orderId) {
        final Order order = getOrder(orderId);
        order.complete();
        return new OrderResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    private Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));
    }

    private OrderTable getOrderTable(UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException("입력한 주문테이블이 존재하지 않습니다."));
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("빈 테이블에는 매장 주문을 등록할 수 없습니다.");
        }
        return orderTable;
    }
}
