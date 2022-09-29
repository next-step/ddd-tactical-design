package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import kitchenpos.eatinorders.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OrderTest {
    @Nested
    class CreateTest {
        @Nested
        class SuccessTest {
            @DisplayName("매장 주문 생성 검증")
            @Test
            void create() {
                // given
                UUID id = UUID.randomUUID();
                OrderStatus orderStatus = OrderStatus.WAITING;
                LocalDateTime orderDateTime = LocalDateTime.now();
                OrderLineItems orderLineItems = new OrderLineItems(TobeFixtures.orderLineItem());
                OrderTable orderTable = TobeFixtures.orderTable(true);

                // when
                Order order = new Order(id, orderStatus, orderDateTime, orderLineItems, orderTable);

                // then
                assertAll(
                    () -> assertThat(order.getId()).isNotNull(),
                    () -> assertThat(order.getStatus()).isEqualTo(orderStatus),
                    () -> assertThat(order.getOrderDateTime()).isEqualTo(orderDateTime),
                    () -> assertThat(order.getOrderLineItems().isEmpty()).isFalse(),
                    () -> assertThat(order.getOrderTable().isOccupied()).isTrue()
                );
            }
        }

        @Nested
        class FailTest {
            @DisplayName("주문항목 목록이 비었을 경우 예외 발생")
            @Test
            void create() {
                // given
                OrderLineItems orderLineItems = new OrderLineItems(Collections.emptyList());

                // when then
                assertThatIllegalArgumentException().isThrownBy(
                    () -> new Order(orderLineItems, TobeFixtures.orderTable(false))
                );
            }

            @DisplayName("숨겨져 있는 주문항목이 있을 경우 예외 발생")
            @Test
            void create2() {
                // given
                OrderLineItems orderLineItems = new OrderLineItems(
                    TobeFixtures.orderLineItem(false),
                    TobeFixtures.orderLineItem(true)
                );

                // when then
                assertThatIllegalStateException().isThrownBy(
                    () -> new Order(orderLineItems, TobeFixtures.orderTable(false))
                );
            }

            @DisplayName("빈 테이블일 경우 예외 발생")
            @Test
            void create3() {
                // given
                OrderTable orderTable = TobeFixtures.orderTable(false);

                // when then
                assertThatIllegalStateException().isThrownBy(
                    () -> new Order(new OrderLineItems(TobeFixtures.orderLineItem()), orderTable)
                );
            }
        }
    }
}
