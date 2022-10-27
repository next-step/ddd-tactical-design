package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderTableNameTest {

    @Test
    @DisplayName("주문 테이블명을 생성한다.")
    void createOrderTableName() {
        // given
        String name = "주문 테이블";

        // when
        OrderTableName orderTableName = new OrderTableName(name);

        // then
        assertThat(orderTableName).isEqualTo(new OrderTableName(name));
    }


    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void createOrderTableName(String name) {
        // when
        // then
        assertThatThrownBy(() -> new OrderTableName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
