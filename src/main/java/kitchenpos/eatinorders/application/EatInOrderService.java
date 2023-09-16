package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.dto.EatInOrderRequest;
import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderException;
import kitchenpos.eatinorders.publisher.OrderTableClearEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuPriceLoader menuPriceLoader;
    private final OrderTableStatusLoader orderTableStatusLoader;
    private final ApplicationEventPublisher publisher;

    public EatInOrderService(EatInOrderRepository eatInOrderRepository, MenuPriceLoader menuPriceLoader, OrderTableStatusLoader orderTableStatusLoader, ApplicationEventPublisher publisher) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuPriceLoader = menuPriceLoader;
        this.orderTableStatusLoader = orderTableStatusLoader;
        this.publisher = publisher;
    }

    @Transactional
    public EatInOrder create(final EatInOrderRequest request) {
        // 주문 테이블 사용중 검증
        if (orderTableStatusLoader.isUnOccupied(request.getOrderTableId())) {
            throw new EatInOrderException(EatInOrderErrorCode.ORDER_TABLE_UNOCCUPIED);
        }
        // 주문 금액 검증
        request.getOrderLineItems()
                .forEach(item -> {
                    Price menuPrice = menuPriceLoader.findMenuPriceById(item.getMenuId());
                    if (menuPrice.isLessThan(item.getPrice())) {
                        throw new EatInOrderException(EatInOrderErrorCode.ORDER_PRICE_IS_GREATER_THAN_MENU);
                    }
                });

        return eatInOrderRepository.save(request.toEntity());
    }

    @Transactional
    public EatInOrder accept(final EatInOrderId orderId) {
        final EatInOrder order = findById(orderId);
        order.accept();
        return order;
    }

    @Transactional
    public EatInOrder serve(final EatInOrderId orderId) {
        final EatInOrder order = findById(orderId);
        order.serve();
        return order;
    }


    @Transactional
    public EatInOrder complete(final EatInOrderId orderId) {
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

        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EatInOrder findById(EatInOrderId eatInOrderId) {
        return eatInOrderRepository.findById(eatInOrderId)
                .orElseThrow(NoSuchElementException::new);
    }
    @Transactional(readOnly = true)
    public boolean areAllOrdersComplete(OrderTableId targetId) {
        return eatInOrderRepository.existsByOrderTableAndStatusNot(targetId, EatInOrderStatus.COMPLETED);
    }
}
