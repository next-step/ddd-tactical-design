package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableNameTest {

    @DisplayName("주문 테이블 이름을 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new OrderTableName("1번"))
                .doesNotThrowAnyException();
    }

    @DisplayName("주문 테이블 이름은 비어있지 않아야 한다")
    @ParameterizedTest
    @NullAndEmptySource
    void createInvalidName(String name) {
        assertThatThrownBy(() -> new OrderTableName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 테이블 이름은 빈 값이 아니어야 합니다. 입력 값 : " + name);
    }
}
