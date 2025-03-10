package kitchenpos.eatinorders.tobe.domain.eatinorder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderLineItemQuantityTest {

    @DisplayName("0이상 숫자로 매장 식사 주문 항목 수량을 생성할 수 있다.")
    @Test
    void create() {
        // given
        long quantity = 1;

        // when
        EatInOrderLineItemQuantity menuProductQuantity = new EatInOrderLineItemQuantity(quantity);

        // then
        assertThat(menuProductQuantity.getValue()).isEqualTo(quantity);
    }

    @DisplayName("최소 개수 미만의 숫자로 매장 식사 주문 항목 수량을 생성 시, 예외가 발생한다.")
    @Test
    void createWithNegativeQuantity() {
        // given
        long quantity = EatInOrderLineItemQuantity.MINIMUM_QUANTITY - 1;
        // when then
        assertThatThrownBy(() -> new EatInOrderLineItemQuantity(quantity))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(EatInOrderLineItemQuantity.ERROR_MESSAGE_VALUE_NULL);
    }
}
