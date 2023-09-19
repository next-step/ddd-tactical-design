package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableTest {

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void invalidName(String name) {
        assertThatThrownBy(() -> OrderTable.create(name, 4))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블을 등록할 수 있다")
    @Test
    void create() {
        OrderTable actual = OrderTable.create("테이블1", 4);
        assertThat(actual).isNotNull();
    }

    @DisplayName("빈 테이블을 해지할 수 있다")
    @Test
    void sit() {
        OrderTable actual = orderTable();
        actual.sit();
        assertThat(actual.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다")
    @Test
    void clear() {
        OrderTable actual = orderTable(true);
        actual.clear();
        assertThat(actual.isOccupied()).isFalse();
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다")
    @Test
    void invalidNumberOfGuests() {
        OrderTable actual = orderTable(true);
        assertThatThrownBy(() -> actual.changeNumberOfGuests(-1))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다")
    @Test
    void changeNumberOfGuests() {
        OrderTable actual = orderTable(true);
        actual.changeNumberOfGuests(10);
        assertThat(actual.getNumberOfGuests()).isEqualTo(new NumberOfGuests(10));
    }

    private OrderTable orderTable() {
        return OrderTable.create("테이블1", 4);
    }

    private OrderTable orderTable(final boolean occupied) {
        return new OrderTable(UUID.randomUUID(), new OrderTableName("테이블1"), new NumberOfGuests(4), occupied);
    }
}
