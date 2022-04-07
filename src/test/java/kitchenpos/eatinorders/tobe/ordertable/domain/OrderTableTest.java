package kitchenpos.eatinorders.tobe.ordertable.domain;

import kitchenpos.OrderTableFixture;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("주문 테이블(OrderTable)은")
class OrderTableTest {
    private static final UUID id = UUID.randomUUID();
    private static final String name = "주문테이블";

    @DisplayName("생성할 수 있다.")
    @Test
    void create() {
        final OrderTable orderTable = new OrderTable(id, name);
        assertThat(orderTable).isEqualTo(new OrderTable(id, name));
        assertThat(orderTable.isEmpty()).isTrue();
    }

    @DisplayName("비어있는 이름으로 생성시 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create(final String name) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new OrderTable(name);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final OrderTable orderTable = OrderTableFixture.주문테이블();
        assertThat(orderTable.isEmpty()).isTrue();

        orderTable.sit();
        assertThat(orderTable.isEmpty()).isFalse();
    }

    @DisplayName("방문 손님수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final OrderTable orderTable = OrderTableFixture.앉은테이블();
        assertThat(orderTable.isEmpty()).isFalse();

        orderTable.changeNumberOfGuests(3);
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(3);
    }

    @DisplayName("변경하려는 손님수가 0명 미만이면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = "-1")
    void changeNumberOfGuests(final int numberOfGuests) {
        final OrderTable orderTable = OrderTableFixture.앉은테이블();

        ThrowableAssert.ThrowingCallable throwingCallable = () -> orderTable.changeNumberOfGuests(numberOfGuests);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

    @DisplayName("빈 테이블에 방문 손님수를 변경하려하면 IllegalStateException이 발생한다.")
    @Test
    void changeNumberOfGuests_empty() {
        final OrderTable orderTable = OrderTableFixture.주문테이블();
        assertThat(orderTable.isEmpty()).isTrue();

        ThrowableAssert.ThrowingCallable throwingCallable = () -> orderTable.changeNumberOfGuests(3);
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(throwingCallable);
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final OrderTable orderTable = OrderTableFixture.앉은테이블();
        assertThat(orderTable.isEmpty()).isFalse();

        orderTable.clear();
        assertThat(orderTable.isEmpty()).isTrue();
    }
}
