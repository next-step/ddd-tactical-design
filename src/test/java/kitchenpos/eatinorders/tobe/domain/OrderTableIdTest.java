package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableIdTest {

    @DisplayName("주문 테이블 key에 null이 입력되면 예외가 발생한다")
    @NullSource
    @ParameterizedTest
    void notNull(UUID id) {
        assertThatThrownBy(() -> new OrderTableId(id))
                .isInstanceOf(NullPointerException.class);
    }

}