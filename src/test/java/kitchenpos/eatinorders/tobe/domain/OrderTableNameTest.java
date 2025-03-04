package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderTableNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableNameTest {

    @DisplayName("주문테이블명 null 또는 공백이 입력되면 예외가 발생한다")
    @NullAndEmptySource
    @ParameterizedTest
    void validate(String name) {
        assertThatThrownBy(() -> new OrderTableName(name))
                .isInstanceOf(InvalidOrderTableNameException.class);
    }
}
