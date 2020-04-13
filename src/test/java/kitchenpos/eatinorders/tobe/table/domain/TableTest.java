package kitchenpos.eatinorders.tobe.table.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.tableEmpty1;
import static kitchenpos.eatinorders.tobe.EatinordersFixtures.tableGrouped1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TableTest {

    @DisplayName("테이블을 생성할 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "provideValidValues")
    void create(final int numberOfGuests, final boolean empty) {
        // given
        // when
        final Table table = new Table(numberOfGuests, empty);

        // then
        assertThat(table.getNumberOfGuests()).isEqualTo(numberOfGuests);
        assertThat(table.isEmpty()).isEqualTo(empty);
    }

    private static Stream<Arguments> provideValidValues() {
        return Stream.of(
                Arguments.arguments(1, true),
                Arguments.arguments(100, false)
        );
    }

    @DisplayName("테이블을 생성 시, 고객 수는 0명 이상이여야한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -10})
    void createFailsWhenNumberOfGuestsLessThan0(final int numberOfGuests) {
        // given
        final boolean empty = true;

        // when
        // then
        assertThatThrownBy(() -> {
            new Table(numberOfGuests, empty);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("공석 상태를 변경할 수 있다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeEmpty(final boolean empty) {
        // given
        final Table table = tableEmpty1();

        // when
        table.changeEmpty(empty);

        // then
        assertThat(table.isEmpty()).isEqualTo(empty);
    }

    @DisplayName("공석 상태 변경 시, 단체테이블에 포함되어있으면 안된다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeEmptyFailsWhenGrouped(final boolean empty) {
        // given
        final Table table = tableGrouped1();

        // when
        // then
        assertThatThrownBy(() -> {
            table.changeEmpty(empty);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("고객 수를 변경할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 100})
    void changeNumberOfGuests(final int numberOfGuests) {
        // given
        final Table table = tableGrouped1();

        // when
        table.changeNumberOfGuests(numberOfGuests);

        // then
        assertThat(table.getNumberOfGuests()).isEqualTo(numberOfGuests);
    }

    @DisplayName("고객 수 변경 시, 0명 이상이여야한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    void changeNumberOfGuestsFailsWhenLessThan0(final int numberOfGuests) {
        // given
        final Table table = tableGrouped1();

        // when
        // then
        assertThatThrownBy(() -> {
            table.changeNumberOfGuests(numberOfGuests);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("고객 수 변경 시, 공석 상태이면 안된다.")
    @Test
    void changeNumberOfGuestsFailsWhenEmpty() {
        // given
        final Table table = tableEmpty1();

        // when
        // then
        assertThatThrownBy(() -> {
            table.changeNumberOfGuests(1);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("테이블을 단체테이블에 지정할 수 있다.")
    @Test
    void group() {
        // given
        final Table table = tableEmpty1();

        // when
        table.group(1L);

        // then
        assertThat(table.getTableGroupId()).isEqualTo(1L);
        assertThat(table.isEmpty()).isFalse();
    }

    @DisplayName("테이블을 단체테이블에서 지정 해제할 수 있다.")
    @Test
    void ungroup() {
        // given
        final Table table = tableGrouped1();

        // when
        table.ungroup();

        // then
        assertThat(table.getTableGroupId()).isNull();
        assertThat(table.isEmpty()).isTrue();
    }
}
