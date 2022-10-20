package kitchenpos.eatinorders.order.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTableCleanUpPolicyTest {

    private EatInOrderRepository eatInOrderRepository;
    private UUID orderTableId;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        orderTableId = UUID.randomUUID();
    }

    @DisplayName("주문테이블을 정리하는 조건")
    @Nested
    class ClearOrderTableCondition {

        @DisplayName("주문테이블의 모든 주문이 완료상태일 경우 True 를 반환한다.")
        @Test
        void isClearOrderTableCondition_true() {
            final EatInOrder eatInOrder = EatInOrderFixture.create(UUID.randomUUID(), orderTableId, EatInOrderStatus.COMPLETED);
            eatInOrderRepository.save(eatInOrder);
            final OrderTableCleanUpPolicy orderTableCleanUpPolicy = new OrderTableCleanUpPolicy(eatInOrderRepository);

            assertThat(orderTableCleanUpPolicy.isCleanUpCondition(orderTableId)).isTrue();
        }

        @ParameterizedTest(name = "주문테이블의 모든 주문이 완료상태가 아닐 경우 False 를 반환한다. status={0}")
        @EnumSource(
                value = EatInOrderStatus.class,
                names = {"COMPLETED"},
                mode = EnumSource.Mode.EXCLUDE)
        void isClearOrderTableCondition_false(final EatInOrderStatus status) {
            final EatInOrder eatInOrder = EatInOrderFixture.create(UUID.randomUUID(), orderTableId, status);
            eatInOrderRepository.save(eatInOrder);
            final OrderTableCleanUpPolicy orderTableCleanUpPolicy = new OrderTableCleanUpPolicy(eatInOrderRepository);

            assertThat(orderTableCleanUpPolicy.isCleanUpCondition(orderTableId)).isFalse();
        }
    }
}
