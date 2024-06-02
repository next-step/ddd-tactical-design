package kitchenpos.orders.tobe.application;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.orders.tobe.application.adapter.MenuServiceAdapter;
import kitchenpos.orders.tobe.application.dto.OrderCreationRequest;
import kitchenpos.orders.tobe.domain.KitchenridersClient;
import kitchenpos.orders.tobe.domain.Order;
import kitchenpos.orders.tobe.domain.OrderLineItems;
import kitchenpos.orders.tobe.domain.OrderRepository;
import kitchenpos.orders.tobe.domain.OrderTableRepository;
import kitchenpos.orders.tobe.domain.factory.OrderFactory;
import kitchenpos.orders.tobe.domain.factory.OrderFactoryProvider;

@Service
public class DeliveryOrderService extends OrderService {

    public DeliveryOrderService(
        OrderRepository orderRepository,
        MenuServiceAdapter menuServiceAdapter,
        OrderTableRepository orderTableRepository,
        KitchenridersClient kitchenridersClient,
        OrderFactoryProvider orderFactoryProvider
    ) {
        super(orderRepository, menuServiceAdapter, orderTableRepository, kitchenridersClient, orderFactoryProvider);
    }

    @Override
    @Transactional
    public Order create(final OrderCreationRequest request) {
        OrderLineItems orderLineItems = createOrderLineItems(request);

        OrderFactory orderFactory = orderFactoryProvider.getFactory(request.type());
        Order order = orderFactory.createOrder(request.type(), orderLineItems, null, request.deliveryAddress());

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order complete(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

        return order.completed();
    }
}