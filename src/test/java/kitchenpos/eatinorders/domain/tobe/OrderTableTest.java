package kitchenpos.eatinorders.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableTest {

    @Test
    @DisplayName("주문 테이블 생성이 가능하다")
    void constructor() {
        final OrderTable expected = new OrderTable(new OrderTableId(UUID.randomUUID()), new OrderTableName("주문 테이블"));
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다")
    @NullAndEmptySource
    void constructor_with_null_and_empty_value(final String name) {
        assertThatThrownBy(() -> new OrderTable(new OrderTableId(UUID.randomUUID()), new OrderTableName(name)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("빈 테이블을 해지할 수 있다")
    void occupied() {
        final OrderTable expected = new OrderTable(new OrderTableId(UUID.randomUUID()), new OrderTableName("주문 테이블"));
        expected.occupy();

        assertThat(expected.isOccupied()).isTrue();
    }

    @Test
    @DisplayName("빈 테이블로 설정할 수 있다")
    void clear() {
        final OrderTable expected = new OrderTable(new OrderTableId(UUID.randomUUID()), new OrderTableName("주문 테이블"));
        expected.occupy();

        assertThat(expected.isOccupied()).isTrue();

        expected.clear();
        assertThat(expected.isOccupied()).isFalse();
        assertThat(expected.guestCount()).isEqualTo(GuestCount.zeroGuestCount());
    }

    @Test
    @DisplayName("방문한 손님 수를 변경할 수 있다")
    void change_guest() {
        final OrderTable expected = new OrderTable(new OrderTableId(UUID.randomUUID()), new OrderTableName("주문 테이블"));
        expected.occupy();

        assertThat(expected.isOccupied()).isTrue();

        final GuestCount changeGuest = new GuestCount(5);
        expected.changeGuest(changeGuest);
        assertThat(expected.guestCount()).isEqualTo(changeGuest);
    }

    @Test
    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다")
    void change_guest_with_not_valid_guest() {
        final OrderTable expected = new OrderTable(new OrderTableId(UUID.randomUUID()), new OrderTableName("주문 테이블"));
        expected.occupy();

        assertThat(expected.isOccupied()).isTrue();

        assertThatThrownBy(() -> expected.changeGuest(new GuestCount(-1)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다")
    void change_guest_at_not_occupied() {
        final OrderTable expected = new OrderTable(new OrderTableId(UUID.randomUUID()), new OrderTableName("주문 테이블"));

        assertThatThrownBy(() -> expected.changeGuest(new GuestCount(10)))
            .isInstanceOf(IllegalStateException.class);
    }
}
