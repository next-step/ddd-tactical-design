package kitchenpos.eatinorders.tobe.application;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.eatinorders.tobe.application.adapter.MenuServiceAdapter;
import kitchenpos.eatinorders.tobe.application.dto.OrderCreationRequest;
import kitchenpos.eatinorders.tobe.domain.KitchenridersClient;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.OrderType;
import kitchenpos.eatinorders.tobe.factory.OrderFactory;
import kitchenpos.eatinorders.tobe.factory.OrderFactoryProvider;

@Service
public class EatInOrderService extends OrderService {

    public EatInOrderService(
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
        if (request.type() != OrderType.EAT_IN) {
            throw new IllegalArgumentException(WRONG_ORDER_TYPE_ERROR);
        }

        OrderLineItems orderLineItems = createOrderLineItems(request);

        OrderTable orderTable = orderTableRepository.findById(request.orderTableId())
                .orElseThrow(() -> new NoSuchElementException(ORDER_TABLE_NOT_FOUND_ERROR));

        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }

        OrderFactory orderFactory = orderFactoryProvider.getFactory(request.type());
        Order order = orderFactory.createOrder(orderLineItems, orderTable, request.deliveryAddress());

        return orderRepository.save(order);
    }
}