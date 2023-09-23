package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableTest {
    private OrderTable orderTable;

    @BeforeEach
    void init() {
        orderTable = new OrderTable("1번 테이블");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void OrderTable의_이름은_없거나_빈_값일_수_없다(String nullAndEmpty) {
        assertThatThrownBy(() -> new OrderTable(nullAndEmpty))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("이름은 없거나 빈 값일 수 없습니다. 현재 값: %s", nullAndEmpty));
    }

    @Test
    void OrderTable을_등록할_수_있다() {
        assertDoesNotThrow(() -> {
            orderTable = new OrderTable("1번 테이블");
        });

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(0);
        assertThat(orderTable.isInUse()).isFalse();
        assertThat(orderTable.isNotInUse()).isTrue();
    }

    @Test
    void 테이블을_사용할_수_있다() {
        orderTable.use();

        assertThat(orderTable.isInUse()).isTrue();
    }

    @Test
    void 사용중인_테이블을_손님_수를_변경할_수_있다() {
        orderTable.use();

        orderTable.changeNumberOfGuests(1);

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(1);
    }

    @Test
    void 사용안함_테이블의_손님_수를_변경하면_예외가_발생한다() {
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사용중이어야 손님 수를 변경할 수 있습니다. 현재 값: false");
    }

    @Test
    void 테이블_정리할_수_있다() {
        orderTable.use();
        orderTable.changeNumberOfGuests(1);

        orderTable.clean();

        assertThat(orderTable.isNotInUse()).isTrue();
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(0);
    }
}
