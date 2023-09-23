package kitchenpos.ordertables.domain;

import kitchenpos.orders.ordertables.domain.OrderTableName;
import kitchenpos.orders.ordertables.exception.OrderTableErrorCode;
import kitchenpos.orders.ordertables.exception.OrderTableNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("주문 테이블 이름")
class OrderTableNameTest {

    @DisplayName("[성공] 주문 테이블 이름을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"name"})
    void create1(String input) {
        assertThatNoException()
                .isThrownBy(() -> new OrderTableName(input));
    }

    @DisplayName("[실패] 주문 테이블 이름은 공백일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create2(String input) {
        assertThatThrownBy(() -> new OrderTableName(input))
                .isInstanceOf(OrderTableNameException.class)
                .hasMessage(OrderTableErrorCode.NAME_IS_EMTPY.getMessage());
    }
}
