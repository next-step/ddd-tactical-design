package kitchenpos.eatinorders.todo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문테이블")
class OrderTableTest {

    private static final OrderTableName 주문테이블_이름 = OrderTableName.from("주문테이블 이름");

    @DisplayName("주문 테이블을 생성한다.")
    @Test
    void create() {
        OrderTable actual = OrderTable.from(주문테이블_이름);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.name()).isEqualTo("주문테이블 이름"),
                () -> assertThat(actual.getNumberOfGuests()).isZero(),
                () -> assertThat(actual.isOccupied()).isFalse()
        );
    }
}
