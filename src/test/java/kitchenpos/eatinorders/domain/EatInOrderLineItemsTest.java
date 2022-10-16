package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderLineItemsTest {

    @DisplayName("주문의 메뉴는 하나 이상이어야 한다.")
    @Test
    void emptyException() {
        assertThatThrownBy(() -> new EatInOrderLineItems(new ArrayList<>()))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문의 메뉴는 1개 이상이어야 합니다.");
    }
}
