package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableTest {

    @DisplayName("정상적으로 생성되는 것 테스트.")
    @Test
    void create() {
        OrderTable result = OrderTable.nonEmptyTable(3);

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getTableGroupId()).isEqualTo(null),
                () -> assertThat(result.getNumberOfGuests()).isEqualTo(3),
                () -> assertThat(result.isEmpty()).isEqualTo(false)
        );
    }

    @DisplayName("빈 테이블 설정 또는 해지할 수 있다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeEmpty(final boolean empty) {
        OrderTable result = new OrderTable(1L, false);

        result.changeEmpty(new OrderTable(1L, empty));

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.isEmpty()).isEqualTo(empty)
        );
    }

    @DisplayName("단체 지정된 주문 테이블은 빈 테이블 설정 또는 해지할 수 없다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeEmptyOfGroupedTable(final boolean empty) {
        OrderTable result = new OrderTable(1L, false);

        result.changeEmpty(new OrderTable(1L, empty));

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.isEmpty()).isEqualTo(empty)
        );
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void changeNumberOfGuests(final int numberOfGuests) {
        OrderTable result = OrderTable.nonEmptyTable(3);

        result.changeNumberOfGuests(OrderTable.nonEmptyTable(numberOfGuests));
        assertThat(result.getNumberOfGuests()).isEqualTo(numberOfGuests);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 입력할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void changeNumberOfGuestsInEmptyTable(final int numberOfGuests) {
        OrderTable result = OrderTable.emptyTable();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> result.changeNumberOfGuests(OrderTable.nonEmptyTable(numberOfGuests)))
        ;
    }

    @DisplayName("단체 지정을 할 수 있다.")
    @Test
    void group() {
        OrderTable result = OrderTable.emptyTable();

        result.group(1L);

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.isEmpty()).isEqualTo(false),
                () -> assertThat(result.getTableGroupId()).isEqualTo(1L)
        );
    }

    @DisplayName("단체 번호가 유효하지 않으면 단체 지정을 할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1, 0})
    void groupFailure(final Long tableGroupId) {
        OrderTable result = OrderTable.emptyTable();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> result.group(tableGroupId))
        ;
    }

    @DisplayName("단체 지정을 해제할 수 있다.")
    @Test
    void ungroup() {
        OrderTable result = OrderTable.nonEmptyTable(3);
        result.ungroup();

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.isEmpty()).isEqualTo(false),
                () -> assertThat(result.getNumberOfGuests()).isEqualTo(3),
                () -> assertThat(result.getTableGroupId()).isEqualTo(null)
        );
    }
}
