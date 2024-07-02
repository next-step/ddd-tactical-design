package kitchenpos.orders.store.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.orders.common.application.OrderLineItemMapper;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.OrderLineItems;
import kitchenpos.orders.store.application.dto.StoreOrderCreateRequest;
import kitchenpos.orders.store.domain.OrderTableRepository;
import kitchenpos.orders.store.domain.StoreOrderRepository;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import kitchenpos.orders.store.domain.tobe.StoreOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreOrderService {

    private final StoreOrderRepository storeOrderRepository;
    private final OrderTableRepository orderTableRepository;
    private final OrderLineItemMapper orderLineItemMapper;

    public StoreOrderService(StoreOrderRepository storeOrderRepository,
        OrderTableRepository orderTableRepository, OrderLineItemMapper orderLineItemMapper) {
        this.storeOrderRepository = storeOrderRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderLineItemMapper = orderLineItemMapper;
    }

    @Transactional
    public StoreOrder create(final StoreOrderCreateRequest request) {
        final OrderLineItems orderLineItems = orderLineItemMapper.map(OrderType.EAT_IN,
            request.orderLineItemRequests());
        final OrderTable orderTable = orderTableRepository.findById(request.orderTableId())
                .orElseThrow(NoSuchElementException::new);

        final StoreOrder storeOrder = new StoreOrder(orderLineItems, orderTable);
        return storeOrderRepository.save(storeOrder);
    }

    @Transactional
    public StoreOrder accept(final UUID orderId) {
        final StoreOrder storeOrder = storeOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        storeOrder.accept();

        return storeOrder;
    }

    @Transactional
    public StoreOrder serve(final UUID orderId) {
        final StoreOrder storeOrder = storeOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        storeOrder.serve();

        return storeOrder;
    }

    @Transactional
    public StoreOrder complete(final UUID orderId) {
        final StoreOrder storeOrder = storeOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        storeOrder.complete();

        return storeOrder;
    }

    @Transactional(readOnly = true)
    public List<StoreOrder> findAll() {
        return storeOrderRepository.findAll();
    }
}
