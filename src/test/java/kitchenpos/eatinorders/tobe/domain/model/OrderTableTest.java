package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.doubles.MemoryEatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException.CANT_CLEAR;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableTest {

    private MemoryEatInOrderRepository eatInOrderRepository;
    private TablePolicy policy;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new MemoryEatInOrderRepository();
        policy = new TablePolicy(eatInOrderRepository);
    }

    @DisplayName("진행중인 주문이 있으면 테이블을 치울 수 없다.")
    @Test
    void clear_Exception() {
        // given
        OrderTable orderTable = new OrderTable("1번 테이블");
        ReflectionTestUtils.setField(orderTable, "id", UUID.randomUUID());
        EatInOrder order = new EatInOrder(orderTable.getId());

        eatInOrderRepository.save(order);

        // expected
        assertThatThrownBy(() -> orderTable.clear(policy))
                .isInstanceOf(IllegalOrderTableStatusException.class)
                .hasMessage(CANT_CLEAR);
    }

    @DisplayName("진행중인 주문이 없으면 테이블을 치울 수 있다.")
    @Test
    void clear() {
        OrderTable orderTable = new OrderTable("1번 테이블");

        assertThatNoException().isThrownBy(() -> orderTable.clear(policy));
    }
}
