package kitchenpos.eatinorders.tobe.eatinorder.application;

import kitchenpos.eatinorders.tobe.eatinorder.domain.*;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.CreateRequest;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final OrderTableLoader orderTableLoader;
    private final OrderLineItemLoader orderLineItemLoader;
    private final MenuClient menuClient;

    public EatInOrderService(final OrderRepository orderRepository, final OrderTableLoader orderTableLoader, final OrderLineItemLoader orderLineItemLoader, final MenuClient menuClient) {
        this.orderRepository = orderRepository;
        this.orderTableLoader = orderTableLoader;
        this.orderLineItemLoader = orderLineItemLoader;
        this.menuClient = menuClient;
    }

    @Transactional
    public EatInOrder create(final CreateRequest request) {
        final OrderLineItems orderLineItems = loadOrderLineItems(request.getOrderLineItemRequests());
        final OrderTable orderTable = loadOrderTable(request.getOrderTableId());
        final EatInOrder order = new EatInOrder(orderLineItems, orderTable.getId());
        return orderRepository.save(order);
    }

    private OrderLineItems loadOrderLineItems(final List<OrderLineItemCreateRequest> requests) {
        return orderLineItemLoader.loadOrderLineItems(requests,
                menuClient.findAllByIdn(
                        requests.stream()
                                .map(OrderLineItemCreateRequest::getMenuId)
                                .collect(toList())
                ));
    }

    private OrderTable loadOrderTable(final UUID orderTableId) {
        return null;
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = findById(orderId);
        order.accept();
        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = findById(orderId);
        order.serve();
        return order;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = findById(orderId);
        order.complete();
        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EatInOrder findById(final UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }
}
