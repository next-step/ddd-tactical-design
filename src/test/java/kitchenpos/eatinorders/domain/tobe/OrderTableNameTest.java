package kitchenpos.eatinorders.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableNameTest {

    @Test
    @DisplayName("주문 테이블 이름을 생성 할 수 있다")
    void constructor() {
        final OrderTableName expected = new OrderTableName("주문 테이블");
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록 할 수 없다")
    @NullAndEmptySource
    void constructor_with_null_and_empty_value(final String value) {
        assertThatThrownBy(() -> new OrderTableName(value))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
