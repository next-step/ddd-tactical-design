package kitchenpos.eatinorders.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderQuantityTest {

    @DisplayName("매장 주문 수량은 음수로 등록할 수 있다.")
    @Test
    void create() {
        assertThatCode(() -> new EatInOrderQuantity(-1))
                .doesNotThrowAnyException();
    }
}
