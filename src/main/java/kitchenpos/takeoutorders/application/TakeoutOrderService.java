package kitchenpos.takeoutorders.application;

import kitchenpos.support.application.OrderLineItemsFactory;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.dto.OrderLineItemCreateRequest;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderRepository;
import kitchenpos.takeoutorders.dto.TakeoutOrderCreateRequest;
import kitchenpos.takeoutorders.dto.TakeoutOrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TakeoutOrderService {
    private final TakeoutOrderRepository orderRepository;
    private final MenuClient menuClient;

    public TakeoutOrderService(
            final TakeoutOrderRepository orderRepository,
            final MenuClient menuClient
    ) {
        this.orderRepository = orderRepository;
        this.menuClient = menuClient;
    }

    @Transactional
    public TakeoutOrderResponse create(final TakeoutOrderCreateRequest request) {
        List<OrderLineItemCreateRequest> orderLineItemsRequest = request.orderLineItems();
        TakeoutOrder order = TakeoutOrder.create(new OrderLineItemsFactory(menuClient).create(orderLineItemsRequest));
        TakeoutOrder result = orderRepository.save(order);
        return TakeoutOrderResponse.from(result);
    }

    @Transactional
    public TakeoutOrderResponse accept(final UUID orderId) {
        final TakeoutOrder order = getOrder(orderId);
        return TakeoutOrderResponse.from(order.accept());
    }

    @Transactional
    public TakeoutOrderResponse serve(final UUID orderId) {
        final TakeoutOrder order = getOrder(orderId);
        return TakeoutOrderResponse.from(order.serve());
    }

    @Transactional
    public TakeoutOrderResponse complete(final UUID orderId) {
        final TakeoutOrder order = getOrder(orderId);
        return TakeoutOrderResponse.from(order.complete());
    }

    @Transactional(readOnly = true)
    public List<TakeoutOrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(TakeoutOrderResponse::from)
                .toList();
    }

    private TakeoutOrder getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }
}
