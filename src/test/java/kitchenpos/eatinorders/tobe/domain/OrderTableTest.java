package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableTest {


    @Test
    @DisplayName("성공")
    void success() {
        OrderTable orderTable = new OrderTable("8번");

        assertThat(orderTable.getId()).isNotNull();
        assertThat(orderTable.getName()).isEqualTo("8번");
        assertThat(orderTable.getNumberOfGuests()).isZero();
        assertThat(orderTable.isOccupied()).isFalse();
    }

    @Test
    @DisplayName("고객 수는 음수 이하가 될 수 없다.")
    void canNotHaveMinusGuests() {
        assertThatThrownBy(() -> new OrderTable(UUID.randomUUID(), "8번", -1, true))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("음수 이하는 입력할 수 없습니다.");
    }

    @Test
    @DisplayName("비어있는 테이블의 손님수를 변경할 수 없다.")
    void canNotChangeGuestsClearTable() {
        //given
        OrderTable orderTable = new OrderTable("9번");

        //when then
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(10))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비어있는 상태 테이블의 손님수는 변경할 수 없습니다.");
    }

}
