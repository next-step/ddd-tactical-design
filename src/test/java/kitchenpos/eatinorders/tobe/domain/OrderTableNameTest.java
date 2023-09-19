package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("주문 테이블의 이름은 비워 둘 수 없다")
    void nullAndEmpty(String name) {
        assertThatThrownBy(() -> new OrderTableName(name))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문 테이블 이름을 생성할 수 있다")
    void constructor() {
        OrderTableName actual = new OrderTableName("테이블");
        assertThat(actual).isNotNull();
    }
}
