package kitchenpos.eatinorders.tobe;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableNameTest {

    @DisplayName("null 이나 공백으로 테이블 이름을 생성시 예외.")
    @ParameterizedTest
    @NullAndEmptySource
    void name_exception(String name) {
        assertThatThrownBy(() -> new OrderTableName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("테이블 이름 생성")
    @Test
    void create() {
        assertDoesNotThrow(() -> new OrderTableName("테이블 명"));
    }
}
