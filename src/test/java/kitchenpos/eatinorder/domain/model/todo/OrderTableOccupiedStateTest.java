package kitchenpos.eatinorder.domain.model.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableOccupiedStateTest {
    @DisplayName("OrderTableOccupiedState를 생성한다.")
    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void create(boolean value) {
        // when
        final OrderTableOccupiedState orderTableOccupiedState = OrderTableOccupiedState.of(value);

        // then
        assertAll(
                () -> assertThat(orderTableOccupiedState).isNotNull(),
                () -> assertThat(orderTableOccupiedState.isOccupied()).isEqualTo(value)
        );
    }

    @DisplayName("OrderTableOccupiedState를 사용중 상태로 변경한다.")
    @Test
    void occupy() {
        // given
        final OrderTableOccupiedState orderTableOccupiedState = OrderTableOccupiedState.of(false);

        // when
        final OrderTableOccupiedState occupiedOrderTable = orderTableOccupiedState.occupy();

        // then
        assertThat(occupiedOrderTable.isOccupied()).isTrue();
        assertThat(occupiedOrderTable.isVacant()).isFalse();
    }

    // test vacate
    @DisplayName("OrderTableOccupiedState를 공석 상태로 변경한다.")
    @Test
    void vacate() {
        // given
        final OrderTableOccupiedState orderTableOccupiedState = OrderTableOccupiedState.of(true);

        // when
        final OrderTableOccupiedState vacantOrderTable = orderTableOccupiedState.vacate();

        // then
        assertThat(vacantOrderTable.isVacant()).isTrue();
        assertThat(vacantOrderTable.isOccupied()).isFalse();
    }
}