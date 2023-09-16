package kitchenpos.ordermaster.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToBeOrderLineItemsTest {
    @DisplayName("주문내역이 없으면 생성불가")
    @Test
    void name() {
        assertThatThrownBy(() -> new ToBeOrderLineItems(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 내역이 없으면 등록할 수 없다.");
    }
}
