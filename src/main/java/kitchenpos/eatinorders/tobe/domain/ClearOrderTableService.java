package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.application.tobe.exception.InvalidOrderTableStateException;
import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/*
주문 테이블을 정리하는 도메인 서비스

주문 repository, 주문 테이블 repositoy
둘 다 사용하는 도메인 서비스

1. 주문에 연결된 주문 테이블 id를 이용한 주문 테이블 정리
2. 주문 테이블 id를 이용한 주문 테이블 정리
* */

@Component
public class ClearOrderTableService {

    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    public ClearOrderTableService(OrderRepository orderRepository, OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public void clearOrderTable(OrderId orderId) {
        //EatInOrderService.complete 메소드에서 사용
        //1. 주문에 연결된 주문 테이블 id를 이용한 주문 테이블 정리
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        OrderTable orderTable = orderTableRepository.findById(order.orderTableId())
                .orElseThrow(NoSuchElementException::new);

        if (!orderRepository.existsByOrderTableAndStatusNot(order.orderTableId(), OrderStatus.COMPLETED)) {
            orderTable.clear();
        }
    }

    @Transactional
    public void clearOrderTable(OrderTableId orderTableId) {
        //OrderTableService.clear 메소드에서 사용
        //2. 주문 테이블 id를 이용한 주문 테이블 정리
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);

        if (orderRepository.existsByOrderTableAndStatusNot(orderTableId, OrderStatus.COMPLETED)) {
            throw new InvalidOrderTableStateException("");
        }
        orderTable.clear();
    }
}