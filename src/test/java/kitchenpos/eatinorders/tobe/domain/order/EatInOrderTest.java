package kitchenpos.eatinorders.tobe.domain.order;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.UUID;
import kitchenpos.common.domain.Money;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableId;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("EatInOrder 는")
class EatInOrderTest {

    private final OrderId id = new OrderId(UUID.randomUUID());
    private final OrderTableId orderTableId = new OrderTableId(UUID.randomUUID());
    private final OrderLineItem orderLineItem = new OrderLineItem(
        new MenuId(UUID.randomUUID()),
        new Money(1000),
        1
    );
    private final OrderLineItems orderLineItems = new OrderLineItems(orderLineItem);

    @DisplayName("등록할 수 있다")
    @Nested
    class 등록할_수_있다 {

        @DisplayName("orderTable이 없는 경우 등록할 수 없다.")
        @ParameterizedTest(name = "{0} 인 경우")
        @NullSource
        void orderTable이_없는_경우_등록할_수_없다(OrderTableId orderTableId) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new EatInOrder(id, orderTableId, orderLineItem));
        }

        @DisplayName("orderLineItem이 없는 경우 등록할 수 없다.")
        @ParameterizedTest(name = "{0} 인 경우")
        @EmptySource
        void orderLineItem이_없는_경우_등록할_수_없다(OrderLineItem... elements) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new EatInOrder(id, orderTableId, elements));
        }

        @DisplayName("orderTable이 존재하고 orderLineItem이 있는 경우 등록할 수 있다.")
        @Test
        void orderTable이_존재하고_orderLineItem이_있는_경우_등록할_수_있다() {
            assertDoesNotThrow(() -> new EatInOrder(id, orderTableId, orderLineItem));
        }
    }

    @DisplayName("접수할 수 있다")
    @Nested
    class 접수할_수_있다 {

        @DisplayName("접수 대기가 아니면 접수할 수 없다.")
        @ParameterizedTest(name = "{0}인 경우")
        @EnumSource(
            value = OrderStatus.class,
            mode = Mode.EXCLUDE,
            names = "WAITING"
        )
        void 접수_대기가_아니면_접수할_수_없다(OrderStatus status) {
            final EatInOrder eatInOrder = new EatInOrder(
                id,
                status,
                orderTableId,
                LocalDateTime.now(),
                orderLineItems
            );
            assertThatIllegalStateException()
                .isThrownBy(() -> eatInOrder.accept());
        }

        @DisplayName("접수 대기 라면 접수할 수 있다.")
        @Test
        void 접수_대기_라면_접수할_수_있다() {
            final EatInOrder eatInOrder = new EatInOrder(id, orderTableId, orderLineItem);
            assertDoesNotThrow(() -> eatInOrder.accept() );
        }
    }

    @DisplayName("서빙할 수 있다")
    @Nested
    class 서빙할_수_있다 {

        @DisplayName("접수가 아니면 서빙할 수 없다.")
        @ParameterizedTest(name = "{0}인 경우")
        @EnumSource(
            value = OrderStatus.class,
            mode = Mode.EXCLUDE,
            names = "ACCEPTED"
        )
        void 접수가_아니면_접수할_수_없다(OrderStatus status) {
            final EatInOrder eatInOrder = new EatInOrder(
                id,
                status,
                orderTableId,
                LocalDateTime.now(),
                orderLineItems
            );
            assertThatIllegalStateException()
                .isThrownBy(() -> eatInOrder.serve());
        }

        @DisplayName("접수 라면 서빙할 수 있다.")
        @Test
        void 접수_라면_접수할_수_있다() {
            final EatInOrder eatInOrder = new EatInOrder(
                id,
                OrderStatus.ACCEPTED,
                orderTableId,
                LocalDateTime.now(),
                orderLineItems
            );
            assertDoesNotThrow(() -> eatInOrder.serve() );
        }
    }

    @DisplayName("완료할 수 있다")
    @Nested
    class 완료할_수_있다 {

        @DisplayName("서빙되지 않으면 완료할 수 없다.")
        @ParameterizedTest(name = "{0}인 경우")
        @EnumSource(
            value = OrderStatus.class,
            mode = Mode.EXCLUDE,
            names = "SERVED"
        )
        void 서빙되지_않으면_완료할_수_없다(OrderStatus status) {
            final EatInOrder eatInOrder = new EatInOrder(
                id,
                status,
                orderTableId,
                LocalDateTime.now(),
                orderLineItems
            );
            assertThatIllegalStateException()
                .isThrownBy(() -> eatInOrder.complete());
        }

        @DisplayName("서빙되었다면 완료할 수 있다.")
        @Test
        void 서빙되었다면_완료할_수_있다() {
            final EatInOrder eatInOrder = new EatInOrder(
                id,
                OrderStatus.SERVED,
                orderTableId,
                LocalDateTime.now(),
                orderLineItems
            );
            assertDoesNotThrow(() -> eatInOrder.complete() );
        }
    }
}
