package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

class OrderTableTest {

    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        orderTable = new OrderTable("1번");
    }

    @DisplayName("주문 테이블 이름으로 주문 테이블을 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new OrderTable("99번"))
                .doesNotThrowAnyException();
    }

    @DisplayName("빈 테이블을 해지한다")
    @Test
    void sit() {
        // when
        orderTable.sit();

        // then
        assertThat(orderTable.isEmpty()).isFalse();
    }

    @DisplayName("방문한 손님 수를 변경한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void changeNumberOfGuests(int numberOfGuests) {
        // given
        orderTable.sit();

        // when
        orderTable.changeNumberOfGuests(numberOfGuests);

        // then
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(numberOfGuests));
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다")
    @Test
    void changeNumberOfGuestsEmptyTable() {
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("빈 테이블은 방문한 손님 수를 변경할 수 없습니다.");
    }

    @DisplayName("방문한 손님 수는 0 이상이어야 한다")
    @Test
    void changeInvalidNumberOfGuests() {
        // given
        orderTable.sit();

        // when
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(-1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("방문한 손님 수는 0 이상이어야 합니다. 입력값 : " + -1);
    }

    @DisplayName("주문 테이블에 매장 주문을 추가한다")
    void addEatInOrder() {
        // given
        EatInOrder eatInOrder = new EatInOrder(Collections.EMPTY_LIST, orderTable);

        // when
        orderTable.addEatInOrder(eatInOrder);

        // then
        assertThat(orderTable.getEatInOrders()).containsExactly(eatInOrder);
    }

    @DisplayName("빈 테이블을 설정한다")
    @Test
    void clear() {
        // when
        orderTable.clear();

        // then
        assertThat(orderTable.isEmpty()).isTrue();
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(0));
    }
}
