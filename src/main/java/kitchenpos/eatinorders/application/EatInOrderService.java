package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.dto.EatInOrderRequest;
import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuPriceLoader menuPriceLoader;
    private final OrderTableStatusLoader orderTableStatusLoader;

    public EatInOrderService(EatInOrderRepository eatInOrderRepository, MenuPriceLoader menuPriceLoader, OrderTableStatusLoader orderTableStatusLoader) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuPriceLoader = menuPriceLoader;
        this.orderTableStatusLoader = orderTableStatusLoader;
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
        // 오더 테이블 지우기.. 테스트 코드 짜기 ㅠㅠ


        order.setStatus(EatInOrderStatus.COMPLETED);
        if (type == OrderType.EAT_IN) {
            final OrderTable orderTable = order.getOrderTable();
            if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
                orderTable.setNumberOfGuests(0);
                orderTable.setOccupied(false);
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

}
