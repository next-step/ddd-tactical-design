package kitchenpos.eatinorders.tobe.domain.order;

import static kitchenpos.eatinorders.tobe.domain.order.fixtures.Fixtures.MENU;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import kitchenpos.common.domain.Money;
import kitchenpos.eatinorders.tobe.domain.order.menu.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("OrderLineItem 은")
class OrderLineItemTest {

    private final Money price = new Money(1_000);

    @DisplayName("생성할 수 있다")
    @Nested
    class 생성할_수_있다 {

        @DisplayName("menu가 없다면 생성할 수 없다")
        @ParameterizedTest(name = "{0}인 경우")
        @NullSource
        void menu가_없다면_생성할_수_없다(Menu menu) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new OrderLineItem(menu, 1));
        }

        @DisplayName("menu가 있다면 생성할 수 있다")
        @Test
        void menu가_있다면_생성할_수_있다() {
            assertDoesNotThrow(() -> new OrderLineItem(MENU, 1));
        }
    }
}
