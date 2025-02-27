package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.vo.PositiveNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTableTest {

    @DisplayName("손님이 주문테이블에 앉는다")
    @Test
    void sit() {
        OrderTable orderTable = createOrderTable("1번테이블", 0, false);

        orderTable.sit();

        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("주문 테이블을 정리한다")
    @Test
    void clear() {
        OrderTable orderTable = createOrderTable("1번테이블", 5, true);

        orderTable.clear();

        assertThat(orderTable.isOccupied()).isFalse();
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(PositiveNumber.ZERO);
    }
    
    @DisplayName("주문 테이블에 앉은 손님의 수를 변경한다")
    @Test
    void changeNumberOfGuests(){
        OrderTable orderTable = createOrderTable("1번테이블", 5, true);

        orderTable.changeNumberOfGuests(3);

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(new PositiveNumber(3));
    }


    private OrderTable createOrderTable(String name, int numberOfGuests, boolean occupied) {
        return new OrderTable(
                OrderTableId.generate(),
                new OrderTableName(name),
                new PositiveNumber(numberOfGuests),
                occupied
        );
    }

}