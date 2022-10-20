package kitchenpos.eatinorders.ordertable.tobe.domain;

import kitchenpos.eatinorders.ordertable.tobe.domain.vo.GuestOfNumbers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static kitchenpos.eatinorders.ordertable.tobe.domain.CleanUpPolicyFixture.FAIL;
import static kitchenpos.eatinorders.ordertable.tobe.domain.CleanUpPolicyFixture.PASS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableCleanUpTest {

    private OrderTableRepository orderTableRepository;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();

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
            final OrderTableCleanUp orderTableCleanUp = new OrderTableCleanUp(PASS, orderTableRepository);

            orderTableCleanUp.clear(orderTable.id());

            final OrderTable result = orderTableRepository.findById(OrderTableCleanUpTest.this.orderTable.id()).get();
            assertThat(result.isEmptyTable()).isTrue();
        }

        @DisplayName("정리할 수 있는 조건일 때만 가능합니다.")
        @Test
        void error() {
            final OrderTableCleanUp orderTableCleanUp = new OrderTableCleanUp(FAIL, orderTableRepository);

            assertThatThrownBy(() -> orderTableCleanUp.clear(orderTable.id()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("정리할 수 있는 조건이 아닙니다.");
        }
    }
}
