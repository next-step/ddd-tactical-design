package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderException;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.fixture.OrderTableFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static kitchenpos.eatinorders.fixture.EatInOrderFixture.orderLineItem;
import static kitchenpos.eatinorders.fixture.EatInOrderFixture.orderLineItems;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("매장 주문")
class EatInOrderTest {

    @DisplayName("매장 주문 생성 ")
    @Test
    void create() {
        EatInOrder eatInOrder = createEatInOrder(EatInOrderStatus.WAITING);
        assertThat(eatInOrder.getStatus().isWaiting()).isTrue();
    }

    @DisplayName("매장 주문 접수")
    @Nested
    class accept {
        @DisplayName("[성공] 대기 상태인 매장 주문만 접수할 수 있다. ")
        @Test
        void accept1() {
            EatInOrder eatInOrder = createEatInOrder(EatInOrderStatus.WAITING);

            eatInOrder.accept();

            assertThat(eatInOrder.getStatus().isAccepted()).isTrue();
        }

        @DisplayName("[실패] 매장 주문이 대기 상태가 아니면 접수할 수 없다.")
        @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
        @ParameterizedTest
        void accept2(final EatInOrderStatus status) {
            EatInOrder eatInOrder = createEatInOrder(status);

            assertThatThrownBy(eatInOrder::accept)
                    .isInstanceOf(EatInOrderException.class)
                    .hasMessage(EatInOrderErrorCode.IS_NOT_WAITING.getMessage());
        }

    }

    @DisplayName("매장 주문 서빙")
    @Nested
    class serve {
        @DisplayName("[성공] 접수된 매장 주문만 서빙될 수 있다. ")
        @Test
        void serve1() {
            EatInOrder eatInOrder = createEatInOrder(EatInOrderStatus.ACCEPTED);

            eatInOrder.serve();

            assertThat(eatInOrder.getStatus().isServed()).isTrue();
        }

        @DisplayName("[실패] 접수된 매장 주문만 서빙될 수 있다. ")
        @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
        @ParameterizedTest
        void serve2(final EatInOrderStatus status) {
            EatInOrder eatInOrder = createEatInOrder(status);

            assertThatThrownBy(eatInOrder::serve)
                    .isInstanceOf(EatInOrderException.class)
                    .hasMessage(EatInOrderErrorCode.IS_NOT_ACCEPTED.getMessage());
        }

    }

    @DisplayName("매장 주문 완료")
    @Nested
    class complete {
        @DisplayName("[성공] 서빙된 매장 주문만 완료될 수 있다. ")
        @Test
        void complete1() {
            EatInOrder eatInOrder = createEatInOrder(EatInOrderStatus.SERVED);

            eatInOrder.complete();

            assertThat(eatInOrder.getStatus().isCompleted()).isTrue();
        }

        @DisplayName("[실패] 서빙된 매장 주문만 완료될 수 있다. ")
        @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
        @ParameterizedTest
        void complete2(final EatInOrderStatus status) {
            EatInOrder eatInOrder = createEatInOrder(status);

            assertThatThrownBy(eatInOrder::complete)
                    .isInstanceOf(EatInOrderException.class)
                    .hasMessage(EatInOrderErrorCode.IS_NOT_SERVED.getMessage());
        }

    }

    private EatInOrder createEatInOrder(EatInOrderStatus status) {
        EatInOrderLineItems eatInOrderLineItems = orderLineItems(orderLineItem());
        OrderTable orderTable = OrderTableFixture.orderTable(true, 4);
        return new EatInOrder(status, eatInOrderLineItems, new OrderTableId(orderTable.getIdValue()));
    }
}
