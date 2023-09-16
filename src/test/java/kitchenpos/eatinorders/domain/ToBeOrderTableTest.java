package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.eatinorders.domain.tobe.domain.NumberOfGuest;
import kitchenpos.eatinorders.domain.tobe.domain.ToBeOrderTable;

class ToBeOrderTableTest {

    private ToBeOrderTable orderTable;

    @BeforeEach
    void setUp() {
        orderTable = new ToBeOrderTable("1번");
    }

    @DisplayName("주문 테이블 이름은 필수 값이다.")
    @ParameterizedTest
    @NullAndEmptySource
    void orderTable1(String name) {
        assertThatThrownBy(() -> new ToBeOrderTable(name))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 필수로 입력되야 합니다.");
    }

    @Test
    void sit() {
        orderTable.sit();
        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("주문 테이블을 치운다.")
    @Test
    void clear1() {
        orderTable.sit();
        orderTable.changeNumberOfGuests(14);
        orderTable.emptyTable(false);
        assertAll(
            () -> assertThat(orderTable.isOccupied()).isFalse(),
            () -> assertThat(orderTable.getNumberOfGuest()).isEqualTo(NumberOfGuest.defaultNumber())
        );
    }

    @DisplayName("완료되지 않은 주문의, 주문 테이블을 치울 수 없다.")
    @Test
    void clear2() {
        orderTable.sit();
        orderTable.changeNumberOfGuests(14);
        assertThatThrownBy(() -> orderTable.emptyTable(true))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.");
    }

    @DisplayName("손님수를 변경한다.")
    @Test
    void changeNumberOfGuests1() {
        orderTable.sit();
        orderTable.changeNumberOfGuests(14);
        assertThat(orderTable.getNumberOfGuest()).isEqualTo(NumberOfGuest.of(14));
    }

    @DisplayName("빈테이블 손님수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuests2() {
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(1))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("빈 테이블이면 손님 수를 변경 할 수 없다.");
    }

    @DisplayName("주문 테이블을 초기화 한다")
    @Test
    void name() {
        orderTable.sit();
        orderTable.changeNumberOfGuests(3);
        orderTable.initOrderTable();
        assertAll(
            () -> assertThat(orderTable.isOccupied()).isFalse(),
            () -> assertThat(orderTable.getNumberOfGuest()).isEqualTo(NumberOfGuest.of(0))
        );
    }
}
