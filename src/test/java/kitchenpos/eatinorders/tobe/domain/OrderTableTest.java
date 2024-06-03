package kitchenpos.eatinorders.tobe.domain;

import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableTest {


    @Test
    @DisplayName("성공")
    void success(){
        OrderTable orderTable = new OrderTable("8번");

        assertThat(orderTable.getId()).isNotNull();
        assertThat(orderTable.getName()).isEqualTo("8번");
        assertThat(orderTable.getNumberOfGuests()).isZero();
        assertThat(orderTable.isOccupied()).isFalse();
    }

}
