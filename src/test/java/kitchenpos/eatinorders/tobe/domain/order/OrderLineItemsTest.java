package kitchenpos.eatinorders.tobe.domain.order;

import static kitchenpos.eatinorders.tobe.domain.order.fixtures.Fixtures.MENU;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("OrderLineItems 은")
class OrderLineItemsTest {

    @DisplayName("생성할 수 있다")
    @Nested
    class 생성할_수_있다 {

        @DisplayName("비어있다면 생성할 수 없다")
        @ParameterizedTest(name = "{0}인 경우")
        @NullAndEmptySource
        void 비어있다면_생성할_수_없다(List<OrderLineItem> elements) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new OrderLineItems(elements));
        }

        @DisplayName("비어있지 않다면 생성할 수 있다")
        @Test
        void 비어있지_않다면_생성할_수_있다() {
            final OrderLineItem orderLineItem = new OrderLineItem(MENU, 1);

            assertDoesNotThrow(() -> new OrderLineItems(orderLineItem));
        }
    }
}
