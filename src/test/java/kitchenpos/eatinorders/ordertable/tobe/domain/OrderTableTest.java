package kitchenpos.eatinorders.ordertable.tobe.domain;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.eatinorders.ordertable.tobe.domain.exception.NoUsedOrderTableException;
import kitchenpos.eatinorders.ordertable.tobe.domain.vo.GuestOfNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableTest {

    @DisplayName("주문 테이블을 생성하면 빈 테이블이 생성된다.")
    @Test
    void create() {
        final OrderTable emptyTable = OrderTable.createEmptyTable("1번 테이블");

        assertAll(
                () -> assertThat(emptyTable.id()).isNotNull(),
                () -> assertThat(emptyTable.name()).isEqualTo(Name.valueOf("1번 테이블")),
                () -> assertThat(emptyTable.guestOfNumbers()).isEqualTo(GuestOfNumbers.ZERO),
                () -> assertThat(emptyTable.isEmptyTable()).isTrue()
        );
    }

    @DisplayName("주문테이블을 사용하면 사용중으로 변경된다.")
    @Test
    void use() {
        final OrderTable orderTable = OrderTable.createEmptyTable("1번 테이블");

        orderTable.use();

        assertThat(orderTable.isEmptyTable()).isFalse();
    }

    @DisplayName("주문테이블을 치우면 빈테이블이 되고 인원수가 0이 된다.")
    @Test
    void clear() {
        final OrderTable orderTable = OrderTable.createEmptyTable("1번 테이블");
        orderTable.use();
        orderTable.changeGuestOfNumbers(GuestOfNumbers.valueOf(5));

        orderTable.clear();

        assertAll(
                () -> assertThat(orderTable.isEmptyTable()).isTrue(),
                () -> assertThat(orderTable.guestOfNumbers()).isEqualTo(GuestOfNumbers.ZERO)
        );
    }

    @DisplayName("주문 테이블의 손님 수를 변경한다")
    @Nested
    class ChangeGuestOfNumbersTest {

        @DisplayName("성공")
        @Test
        void success() {
            final OrderTable orderTable = OrderTable.createEmptyTable("1번 테이블");
            orderTable.use();

            orderTable.changeGuestOfNumbers(GuestOfNumbers.valueOf(5));

            assertThat(orderTable.guestOfNumbers()).isEqualTo(GuestOfNumbers.valueOf(5));
        }

        @DisplayName("비어있는 주문 테이블의 손님 수를 변경할 수 없다.")
        @Test
        void error() {
            final OrderTable emptyTable = OrderTable.createEmptyTable("1번 테이블");

            assertThatThrownBy(() -> emptyTable.changeGuestOfNumbers(GuestOfNumbers.valueOf(5)))
                    .isInstanceOf(NoUsedOrderTableException.class);
        }
    }
}
