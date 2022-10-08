package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.InMemoryEatInOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableClearPolicyTest {
    private EatInOrderRepository eatInOrderRepository;
    private OrderTableClearPolicy orderTableClearPolicy;

    private OrderTable orderTable;
    private EatInOrder eatInOrder;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        orderTableClearPolicy = new OrderTableClearPolicy(eatInOrderRepository);

        orderTable = new OrderTable(1L, "1번 테이블");
        eatInOrder = new EatInOrder(List.of(new OrderLineItem(1L, BigDecimal.valueOf(20000), 2)), 1L);
        eatInOrderRepository.save(eatInOrder);
    }

    @DisplayName("주문 테이블을 치운다.")
    @Test
    void clear() {
        eatInOrder.accept();
        eatInOrder.serve();
        eatInOrder.complete();

        assertDoesNotThrow(() -> orderTableClearPolicy.clear(orderTable));
    }

    @DisplayName("완료되지 않은 주문이 있는 테이블을 치울 수 없다.")
    @Test
    void clearOrderTableWithNotCompleteOrder() {
        assertThatThrownBy(() -> orderTableClearPolicy.clear(orderTable))
                .isInstanceOf(IllegalStateException.class);
    }
}
