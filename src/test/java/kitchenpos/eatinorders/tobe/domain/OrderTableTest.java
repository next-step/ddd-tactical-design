package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.exception.InvalidNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class OrderTableTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 비어있으면 주문 테이블 생성 실패")
    void createOrderTableFail(String name) {
        Assertions.assertThatExceptionOfType(InvalidNameException.class)
                  .isThrownBy(() -> OrderTable.create(name));
    }

    @Test
    @DisplayName("주문 테이블 생성 시 빈 테이블 상태로 생성")
    void createOrderTableSuccess() {
        // given
        String expected = "name";

        // when
        OrderTable actual = OrderTable.create(expected);

        // then
        assertThat(actual.getName()).isEqualTo(expected);
        assertThat(actual.getNumberOfGuests()).isZero();
        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("이미 사용 중인 테이블엔 착석 불가능")
    void sitFail() {
        // given
        OrderTable orderTable = OrderTable.create("name");
        orderTable.sit();

        // when
        assertThatIllegalStateException().isThrownBy(orderTable::sit);
    }

    @Test
    @DisplayName("사용중이 아닌 테이블엔 착석 가능")
    void sitSuccess() {
        // given
        OrderTable orderTable = OrderTable.create("name");

        // when
        assertThatCode(orderTable::sit).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, -2, -10, -1000, -10000 })
    @DisplayName("손님 수 변경 시 0 미만의 수를 입력하면 오류 발생")
    void changeNumberOfGuestsFail01(int guests) {
        // given
        OrderTable orderTable = OrderTable.create("name");

        // when
        assertThatIllegalArgumentException()
            .isThrownBy(() -> orderTable.changeNumberOfGuests(guests));
    }

    @Test
    @DisplayName("사용 중이 아닌 테이블은 손님 수 변경 불가능")
    void changeNumberOfGuestsFail02() {
        // given
        OrderTable orderTable = OrderTable.create("name");

        // when
        assertThatIllegalStateException()
            .isThrownBy(() -> orderTable.changeNumberOfGuests(5));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 5, 10 })
    @DisplayName("사용중이 아닌 테이블엔 착석 가능")
    void changeNumberOfGuestsSuccess(int guests) {
        // given
        OrderTable orderTable = OrderTable.create("name");
        orderTable.sit();

        // when
        assertThatCode(() -> orderTable.changeNumberOfGuests(guests))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사용 중이 아닌 테이블은 정리 불가능")
    void clearFail() {
        // given
        OrderTable orderTable = OrderTable.create("name");

        // when
        assertThatIllegalStateException().isThrownBy(orderTable::clear);
    }

    @Test
    @DisplayName("사용 중인 테이블은 정리 가능")
    void clearSuccess() {
        // given
        OrderTable orderTable = OrderTable.create("name");
        orderTable.sit();

        // when
        orderTable.clear();

        // then
        assertThat(orderTable.isEmpty()).isTrue();
        assertThat(orderTable.getNumberOfGuests()).isZero();
    }
}
