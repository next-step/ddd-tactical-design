package kitchenpos.eatinorder.tobe.domain.ordertable;

import kitchenpos.exception.CanNotChange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableTest {

    private OrderTableName tableName;
    private NumberOfGuests numOfGuests;

    @BeforeEach
    void setUp() {
        tableName = OrderTableName.of("정상이름");
        numOfGuests = NumberOfGuests.of(0);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final var orderTable = OrderTable.of(tableName);

        assertAll(
                () -> assertThat(orderTable).isNotNull(),
                () -> assertThat(orderTable.getId()).isNotNull(),
                () -> assertThat(orderTable.getNumberOfGuests()).isZero(),
                () -> assertThat(orderTable.isOccupied()).isFalse()
        );
    }

    @DisplayName("테이블을 착석상태로 할 수 있다.")
    @Test
    void sit() {
        final var orderTable = OrderTable.of(tableName);
        orderTable.sitted();

        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final var orderTable = OrderTable.of(tableName);
        orderTable.cleared(clearedTable);

        assertAll(
                () -> assertThat(orderTable.getNumberOfGuests()).isZero(),
                () -> assertThat(orderTable.isOccupied()).isFalse()
        );
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final var orderTable = OrderTable.of(tableName);
        assertThat(orderTable.getNumberOfGuests()).isZero();

        orderTable.sitted();
        orderTable.changeNumOfGuests(3);

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(3);
    }

    @DisplayName("방문한 손님 수가 0명 미만이면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int input) {
        final var orderTable = OrderTable.of(tableName);
        assertThat(orderTable.getNumberOfGuests()).isZero();

        orderTable.sitted();
        assertThrowsExactly(IllegalArgumentException.class, () -> orderTable.changeNumOfGuests(input));
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final var orderTable = OrderTable.of(tableName);

        assertThat(orderTable.isOccupied()).isFalse();
        assertThrowsExactly(CanNotChange.class, () -> orderTable.changeNumOfGuests(1));

    }

}
