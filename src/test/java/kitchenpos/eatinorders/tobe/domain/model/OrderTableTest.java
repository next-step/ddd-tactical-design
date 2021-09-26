package kitchenpos.eatinorders.tobe.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTableTest {

    private static final String DEFAULT_NAME = "테이블1";

    @DisplayName("주문 테이블을 생성할 수 있다.")
    @Test
    void ok() {
        final OrderTable orderTable = new OrderTable(DEFAULT_NAME, 0);
        assertThat(orderTable).isNotNull();
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void emptyName(final String emptyName) {
        assertThatThrownBy(() -> new OrderTable(emptyName, 0))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("방문한 손님 수가 음수라면 등록할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, Integer.MIN_VALUE})
    void nagativeNumberOfGuests(final int numberOfGuests) {
        assertThatThrownBy(() -> new OrderTable(DEFAULT_NAME, numberOfGuests))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final OrderTable orderTable = new OrderTable(DEFAULT_NAME, 0);
        final OrderTable satTable = orderTable.sit();
        assertThat(satTable.isEmpty()).isFalse();
    }

    @DisplayName("주문 테이블의 orderStatus가 COMPLETED 인 경우 빈 테이블로 설정할 수 있다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = "COMPLETED", mode = EnumSource.Mode.INCLUDE)
    void clear(final OrderStatus orderStatus) {
        final OrderTable orderTable = new OrderTable(DEFAULT_NAME, 0);
        final OrderTable clearedTable = orderTable.clear(orderStatus);
        assertAll(
            () -> assertThat(clearedTable.isEmpty()).isTrue(),
            () -> assertThat(clearedTable.getNumberOfGuests()).isEqualTo(NumberOfGuests.ZERO)
        );
    }

    @DisplayName("주문 테이블의 orderStatus가 COMPLETED 아닌 경우 빈 테이블로 설정할 수 없다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = "COMPLETED", mode = EnumSource.Mode.EXCLUDE)
    void canNotClear(final OrderStatus orderStatus) {
        final OrderTable orderTable = new OrderTable(DEFAULT_NAME, 0);

        assertThatThrownBy(() -> orderTable.clear(orderStatus))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, Integer.MAX_VALUE})
    void changeNumberOfGuests(final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable(DEFAULT_NAME, 0);
        final OrderTable satTable = orderTable.sit();
        final OrderTable changedOrderTable = satTable.changeNumberOfGuests(numberOfGuests);

        assertThat(changedOrderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(numberOfGuests));
    }

    @DisplayName("방문한 손님 수가 음수라면 변경할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, Integer.MIN_VALUE})
    void canNotChangeNumberOfGuests(final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable(DEFAULT_NAME, 0);
        final OrderTable satTable = orderTable.sit();

        assertThatThrownBy(() -> satTable.changeNumberOfGuests(numberOfGuests))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void emptyTableCanNotChangeNumberOfGuests() {
        final OrderTable orderTable = new OrderTable(DEFAULT_NAME, 0);

        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(1))
            .isInstanceOf(IllegalStateException.class);
    }

}
