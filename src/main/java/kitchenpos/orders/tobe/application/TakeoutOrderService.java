package kitchenpos.orders.tobe.application;

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
public class TakeoutOrderService extends OrderService {

    public TakeoutOrderService(
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
        Order order = orderFactory.createOrder(request.type(), orderLineItems, null, null);

        return orderRepository.save(order);
    }
}