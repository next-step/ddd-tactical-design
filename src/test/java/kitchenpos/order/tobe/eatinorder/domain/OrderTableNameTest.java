package kitchenpos.order.tobe.eatinorder.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableNameTest {

    @DisplayName("주문 테이블 이름을 생성한다")
    @Test
    void testInit() {
        // given
        String name = "주문 테이블 이름";

        // when // then
        assertDoesNotThrow(() -> new OrderTableName(name));
    }

    @DisplayName("유효하지 않은 값으로는 주문 테이블 이름 객체를 생성할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void testInitIfNotValidName(String name) {
        // when // then
        assertThatThrownBy(() -> new OrderTableName(name))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
