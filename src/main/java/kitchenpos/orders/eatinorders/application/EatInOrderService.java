package kitchenpos.orders.eatinorders.application;

import kitchenpos.orders.eatinorders.domain.EatInOrder;
import kitchenpos.orders.eatinorders.domain.EatInOrderId;
import kitchenpos.orders.eatinorders.domain.EatInOrderRepository;
import kitchenpos.orders.eatinorders.dto.EatInOrderRequest;
import kitchenpos.orders.eatinorders.dto.EatInOrderResponse;
import kitchenpos.orders.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.orders.eatinorders.exception.EatInOrderException;
import kitchenpos.orders.eatinorders.exception.EatInOrderLineItemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuLoader menuLoader;
    private final OrderTableStatusLoader orderTableStatusLoader;

    public EatInOrderService(EatInOrderRepository eatInOrderRepository, MenuLoader menuLoader, OrderTableStatusLoader orderTableStatusLoader) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuLoader = menuLoader;
        this.orderTableStatusLoader = orderTableStatusLoader;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderRequest request) {
        // 주문 테이블 사용중 검증
        if (orderTableStatusLoader.isUnOccupied(request.getOrderTableId())) {
            throw new EatInOrderException(EatInOrderErrorCode.ORDER_TABLE_UNOCCUPIED);
        }
        // null, empty 검증
        if (request.getOrderLineItems() == null || request.getOrderLineItems().isEmpty()) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.ORDER_LINE_ITEMS_IS_EMPTY);
        }


        EatInOrder eatInOrder = eatInOrderRepository.save(request.toEntity(menuLoader));
        return EatInOrderResponse.fromEntity(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse accept(final EatInOrderId orderId) {
        final EatInOrder order = findById(orderId);
        order.accept();
        return EatInOrderResponse.fromEntity(order);
    }

    @Transactional
    public EatInOrderResponse serve(final EatInOrderId orderId) {
        final EatInOrder order = findById(orderId);
        order.serve();
        return EatInOrderResponse.fromEntity(order);
    }


    @Transactional
    public EatInOrderResponse complete(final EatInOrderId orderId) {
        final EatInOrder order = findById(orderId);
        order.complete();
        return EatInOrderResponse.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        List<EatInOrder> eatInOrders = eatInOrderRepository.findAll();
        return EatInOrderResponse.fromEntities(eatInOrders);
    }

    private EatInOrder findById(EatInOrderId eatInOrderId) {
        return eatInOrderRepository.findById(eatInOrderId)
                .orElseThrow(NoSuchElementException::new);
    }

}
