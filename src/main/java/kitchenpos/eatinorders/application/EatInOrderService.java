package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderId;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.dto.EatInOrderRequest;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderException;
import kitchenpos.eatinorders.exception.EatInOrderLineItemException;
import kitchenpos.eatinorders.publisher.OrderTableClearEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final OrderLinePolicy orderLinePolicy;
    private final OrderTableStatusLoader orderTableStatusLoader;
    private final ApplicationEventPublisher publisher;

    public EatInOrderService(EatInOrderRepository eatInOrderRepository, OrderLinePolicy orderLinePolicy, OrderTableStatusLoader orderTableStatusLoader, ApplicationEventPublisher publisher) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.orderLinePolicy = orderLinePolicy;
        this.orderTableStatusLoader = orderTableStatusLoader;
        this.publisher = publisher;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderRequest request) {
        // null, empty 검증
        if (request.getOrderLineItems() == null || request.getOrderLineItems().isEmpty()) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.ORDER_LINE_ITEMS_IS_EMPTY);
        }
        // 주문 테이블 사용중 검증
        if (orderTableStatusLoader.isUnOccupied(request.getOrderTableId())) {
            throw new EatInOrderException(EatInOrderErrorCode.ORDER_TABLE_UNOCCUPIED);
        }

        EatInOrder response = eatInOrderRepository.save(request.toEntity(orderLinePolicy));
        return EatInOrderResponse.fromEntity(response);
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

        if (!eatInOrderRepository.existsByOrderTableAndStatusNot
                (order.getOrderTableId(), EatInOrderStatus.COMPLETED)) {
            try {
                publisher.publishEvent(new OrderTableClearEvent(this, order.getOrderTableIdValue()));
            } catch (RuntimeException ex) {
                throw new EatInOrderException(EatInOrderErrorCode.ORDER_TABLE_CANNOT_CLEAR);
            }
        }

        return EatInOrderResponse.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        List<EatInOrder> responses = eatInOrderRepository.findAll();
        return EatInOrderResponse.fromEntities(responses);
    }

    private EatInOrder findById(EatInOrderId eatInOrderId) {
        return eatInOrderRepository.findById(eatInOrderId)
                .orElseThrow(NoSuchElementException::new);
    }

}
