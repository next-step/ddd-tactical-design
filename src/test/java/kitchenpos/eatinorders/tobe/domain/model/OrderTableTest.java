package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Waiting;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableTest {

    @DisplayName("주문 테이블을 생성한다.")
    @Test
    void 생성_성공() {
        final UUID uuid = UUID.randomUUID();
        final DisplayedName displayedName = new DisplayedName("1번 테이블");
        final NumberOfGuests numberOfGuests = new NumberOfGuests(0L);

        final OrderTable orderTable = new OrderTable(uuid, displayedName, numberOfGuests);

        assertAll(
                () -> assertThat(orderTable.getId()).isNotNull(),
                () -> assertThat(orderTable.getName()).isEqualTo(displayedName.value()),
                () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(numberOfGuests.value())
        );
    }

    @DisplayName("주문 테이블은 빈 테이블이 해제된다.")
    @Test
    void 빈_테이블_해제() {
        final OrderTable orderTable = DEFAULT_ORDER_TABLE();

        orderTable.sit();

        assertThat(orderTable.isEmpty()).isFalse();
    }

    @DisplayName("주문 테이블은 빈 테이블로 설정된다.")
    @Test
    void 빈_테이블_설정_성공() {
        final OrderTable orderTable = DEFAULT_ORDER_TABLE();

        orderTable.clear();

        assertThat(orderTable.isEmpty()).isTrue();
    }

    @DisplayName("주문 테이블의 주문 식별자 목록에 계산 완료되지 않은 주문이 있으면 IllegalStateException을 던진다.")
    @Test
    void 빈_테이블_설정_실패() {
        final OrderTable orderTable = DEFAULT_ORDER_TABLE();
        ORDER_WITH_ORDER_TABLE(orderTable);

        final ThrowableAssert.ThrowingCallable when = orderTable::clear;

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage("계산 완료되지 않은 주문이 있습니다.");
    }

    @DisplayName("주문 테이블은 방문한 손님 수가 변경된다.")
    @Test
    @PositiveOrZero
    void 방문한_손님_수_변경_성공(final long expected) {
        final OrderTable orderTable = DEFAULT_ORDER_TABLE();
        final NumberOfGuests numberOfGuests = new NumberOfGuests(expected);

        orderTable.changeNumberOfGuests(numberOfGuests);

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(expected);
    }

    @DisplayName("주문 테이블이 빈 테이블일 경우, 방문한 손님 수를 변경하면 IllegalStateException을 던진다.")
    @Test
    @PositiveOrZero
    void 방문한_손님_수_변경_실패(final long expected) {
        final OrderTable orderTable = DEFAULT_ORDER_TABLE();
        final NumberOfGuests numberOfGuests = new NumberOfGuests(expected);

        final ThrowableAssert.ThrowingCallable when = () -> orderTable.changeNumberOfGuests(numberOfGuests);

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage("빈 테이블의 방문한 손님 수는 0에서 변경할 수 없습니다.");
    }

    private OrderTable DEFAULT_ORDER_TABLE() {
        return new OrderTable(UUID.randomUUID(), new DisplayedName("1번 테이블"), new NumberOfGuests(0L));
    }

    private Order ORDER_WITH_ORDER_TABLE(final OrderTable orderTable) {
        return new Order(
                UUID.randomUUID(),
                orderTable.getId(),
                new Waiting(),
                new OrderLineItems(
                        Collections.singletonList(
                                new OrderLineItem(
                                        UUID.randomUUID(),
                                        UUID.randomUUID(),
                                        new Price(BigDecimal.valueOf(16_000L)),
                                        1L
                                )
                        )
                ),
                order -> {
                }
        );
    }
}
