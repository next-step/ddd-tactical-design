package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemsTest {
    @DisplayName("주문항목 목록이 비었을 경우 예외 발생")
    @Test
    void validate() {
        assertThatIllegalArgumentException().isThrownBy(
            () -> new OrderLineItems(Collections.emptyList())
        );
    }
}
