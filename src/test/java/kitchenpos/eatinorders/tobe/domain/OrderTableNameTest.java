package kitchenpos.eatinorders.tobe.domain;

import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableNameTest {


    @Test
    @DisplayName("성공")
    void success(){
        OrderTableName name = new OrderTableName("9번");

        assertThat(name.getValue()).isEqualTo("9번");
    }

    @Test
    @DisplayName("주문 테이블은 비워둘 수 없다.")
    void canNotEmpty(){

        assertThatThrownBy(() -> new OrderTableName())
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
