package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("주문 테이블 테스트")
public class OrderTableTest {

    @DisplayName("주문 테이블 이름이 없거나 비어있으면 생성할 수 없다.")
    @ParameterizedTest(name = "주문 테이블 이름 : {0}")
    @NullAndEmptySource
    void createOrderTableWithEmptyName(final String name) {
        assertThatThrownBy(() -> new OrderTable(UUID.randomUUID(), name, 0, false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 사용중인 테이블로 변경한다.")
    @Test
    void sit() {
        final OrderTable orderTable = new OrderTable(UUID.randomUUID(), "1번", 0, false);
        orderTable.sit();

        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("방문한 손님 수가 0명 미만이라면 주문 테이블의 손님 수를 변경할 수 없다.")
    @ValueSource(ints = {-1, -10, -1000})
    @ParameterizedTest(name = "{index}. 방문한 손님 수 : {0}")
    void changeNumberOfGuestsWithNegativeNumberOfGuests(final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable(UUID.randomUUID(), "1번", 0, true);
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(numberOfGuests))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사용중인 테이블이 아니라면 주문 테이블의 손님 수를 변경할 수 없다.")
    @ValueSource(ints = {0, 1, 10, 100})
    @ParameterizedTest(name = "{index}. 방문한 손님 수 : {0}")
    void changeNumberOfGuestsWithNotOccupied(final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable(UUID.randomUUID(), "1번", numberOfGuests, false);
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(numberOfGuests))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
