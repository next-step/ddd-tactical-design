package kitchenpos.order.eatinorder.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("주문 테이블의 이름이 비어있거나, null이면 예외를 던진다.")
    void create_order_table_name_exception(String name) {
        // when // then
        assertThatThrownBy(() -> new OrderTableName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 테이블 이름을 채워주세요!");
    }

}