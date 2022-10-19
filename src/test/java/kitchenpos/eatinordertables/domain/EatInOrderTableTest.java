package kitchenpos.eatinordertables.domain;

import static kitchenpos.eatinordertables.EatInOrderTableFixtures.eatInOrderTable;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTableTest {

    @DisplayName("테이블이 착석 상태가 아닐 경우 손님 수를 변경할 수 없다.")
    @Test
    void notOccupiedException() {
        EatInOrderTable eatInOrderTable = eatInOrderTable(false, 0);
        assertThatThrownBy(() -> eatInOrderTable.changeNumberOfGuests(5))
            .isExactlyInstanceOf(IllegalStateException.class)
            .hasMessage("착석중이지 않아 손님 수를 변경할 수 없습니다.");
    }
}
