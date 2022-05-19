package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @DisplayName("수량을 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new Quantity(1L))
                .doesNotThrowAnyException();
    }
}
