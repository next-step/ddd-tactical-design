package kitchenpos.eatinorder.domain.model.todo;

import kitchenpos.shared.domain.Profanities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableTest {
    @DisplayName("OrderTable을 생성한다")
    @Test
    void create() {
        // given
        final UUID id = UUID.randomUUID();
        final String name = "테이블";
        final Profanities profanities = nm -> false;

        // when
        final OrderTable orderTable = OrderTable.createEmptyTable(id, name, profanities);

        // then
        assertAll(
                () -> assertThat(orderTable).isNotNull(),
                () -> assertThat(orderTable.getId()).isEqualTo(id),
                () -> assertThat(orderTable.getName()).isEqualTo(name),
                () -> assertThat(orderTable.getNumberOfGuests()).isZero(),
                () -> assertThat(orderTable.isOccupied()).isFalse()
        );
    }

    @DisplayName("OrderTable를 처음 등록하면 EmptyTable 상태이다")
    @Test
    void createEmptyTable() {
        // given
        final String name = "테이블";
        final Profanities profanities = nm -> false;

        // when
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), name, profanities);

        // then
        assertAll(
                () -> assertThat(orderTable).isNotNull(),
                () -> assertThat(orderTable.isEmpty()).isTrue()
        );
    }

    @DisplayName("OrderTable을 사용중 상태로 변경한다")
    @Test
    void sit() {
        // given
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), "테이블", nm -> false);

        // when
        orderTable.sit();

        // then
        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("OrderTable을 공석 상태로 변경한다")
    @Test
    void clear() {
        // given
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), "테이블", nm -> false);
        orderTable.sit();

        // when
        orderTable.clear();

        // then
        assertThat(orderTable.isEmpty()).isTrue();
    }

    @DisplayName("OrderTable의 손님 수를 변경한다")
    @ValueSource(ints = {0, 1})
    @ParameterizedTest
    void changeNumberOfGuests(int numberOfGuests) {
        // given
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), "테이블", nm -> false);
        orderTable.sit();

        // when
        orderTable.changeNumberOfGuests(numberOfGuests);

        // then
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(numberOfGuests);
    }

    @DisplayName("OrderTable의 손님 수를 변경할 때 손님 수가 0 미만인 경우 예외를 던진다")
    @Test
    void changeNumberOfGuestsWithNegativeValue() {
        // given
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), "테이블", nm -> false);
        orderTable.sit();

        // when
        final Throwable thrown = catchThrowable(() -> orderTable.changeNumberOfGuests(-1));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("손님 수는 0 미만일 수 없습니다.");
    }

    @DisplayName("EmptyTable의 손님 수를 변경할 수 없다")
    @Test
    void changeNumberOfGuestsOfEmptyTable() {
        // given
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), "테이블", nm -> false);

        // when
        final Throwable thrown = catchThrowable(() -> orderTable.changeNumberOfGuests(1));

        // then
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                .hasMessage("빈 테이블의 손님 수는 변경할 수 없습니다.");
    }
}