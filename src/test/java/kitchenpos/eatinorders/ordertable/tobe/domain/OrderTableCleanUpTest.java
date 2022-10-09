package kitchenpos.eatinorders.ordertable.tobe.domain;

import kitchenpos.eatinorders.order.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.order.tobe.domain.EatInOrderFixture;
import kitchenpos.eatinorders.order.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.order.tobe.domain.EatInOrderStatus;
import kitchenpos.eatinorders.order.tobe.domain.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.ordertable.tobe.domain.vo.GuestOfNumbers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableCleanUpTest {

    private EatInOrderRepository eatInOrderRepository;
    private OrderTableRepository orderTableRepository;
    private OrderTableCleanUp orderTableCleanUp;

    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        orderTableCleanUp = new OrderTableCleanUp(orderTableRepository);

        orderTable = OrderTable.createEmptyTable("1번테이블");
        orderTable.use();
        orderTable.changeGuestOfNumbers(GuestOfNumbers.valueOf(3));
        orderTableRepository.save(orderTable);
    }

    @DisplayName("주문 테이블을 정리한다.")
    @Nested
    class ClearTest {

        @DisplayName("성공")
        @Test
        void success() {
            final EatInOrder eatInOrder = EatInOrderFixture.create(UUID.randomUUID(), orderTable.id(), EatInOrderStatus.COMPLETED);
            eatInOrderRepository.save(eatInOrder);

            orderTableCleanUp.clear(orderTable.id(), eatInOrderRepository);

            final OrderTable result = orderTableRepository.findById(OrderTableCleanUpTest.this.orderTable.id()).get();
            assertThat(result.isEmptyTable()).isTrue();
        }

        @ParameterizedTest(name = "주문 테이블에 완료되지 않은 주문이 있으면 정리할 수 없다. status={0}")
        @EnumSource(
                value = EatInOrderStatus.class,
                names = {"COMPLETED"},
                mode = EnumSource.Mode.EXCLUDE)
        void error(final EatInOrderStatus status) {
            final EatInOrder eatInOrder = EatInOrderFixture.create(UUID.randomUUID(), orderTable.id(), status);
            eatInOrderRepository.save(eatInOrder);

            assertThatThrownBy(() -> orderTableCleanUp.clear(orderTable.id(), eatInOrderRepository))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("완료되지 않은 주문이 있습니다.");
        }
    }
}
