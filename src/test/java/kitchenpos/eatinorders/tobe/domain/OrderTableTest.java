package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.eatinorders.tobe.domain.OrderTable.OrderTableStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTableTest {

    @DisplayName("주문테이블을 생성할수 있다")
    @Test
    void test1() {
        //given
        OrderTableName orderTableName = new OrderTableName("테이블이름");

        //when
        OrderTable orderTable = new OrderTable(orderTableName);

        //then
        assertAll(
            () -> assertThat(orderTable.getId()).isNotNull(),
            () -> assertThat(orderTable.getName()).isEqualTo(new OrderTableName("테이블이름")),
            () -> assertThat(orderTable.getStatus()).isEqualTo(OrderTableStatus.VACANT),
            () -> assertThat(orderTable.getNumberOfGuest()).isEqualTo(new NumberOfGuest(0))
        );
    }

    @DisplayName("주문테이블엔 이름이 있어야 한다")
    @Test
    void test2() {
        //when && then
        assertThatThrownBy(
            () -> new OrderTable(null)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문테이블은 이름을 가지고 있어야 합니다");
    }

}