package kitchenpos.eatinorders.tobe.domain.orderTable;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidEatInOrderTableException;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 주문 테이블의 도메인 객체(EatInOrderTable) 내부에서
 * 완료되지 않은 주문이 존재하는지 여부를 판단하기 위한 도메인 서비스 구현체입니다.
 *
 * 응용 서비스가 아닌 도메인 계층에서 유효성 검증 책임을 갖기 위해
 * OrderTableOrders 인터페이스로 추상화하고, 해당 구현을 여기서 수행합니다.
 */
@Component
public class DefaultOrderTableOrders implements OrderTableOrders {

    private final EatInOrderRepository eatInOrderRepository;
    private final EatInOrderTableRepository eatInOrderTableRepository;

    public DefaultOrderTableOrders(final EatInOrderRepository eatInOrderRepository,
                                   final EatInOrderTableRepository eatInOrderTableRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    @Override
    public boolean existByOrderTableId(final UUID orderTableId) {
        final EatInOrderTable orderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new InvalidEatInOrderTableException("주문 테이블이 존재해야 합니다."));

        return eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED);
    }
}
