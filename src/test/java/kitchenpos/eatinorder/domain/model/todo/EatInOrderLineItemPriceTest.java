package kitchenpos.eatinorder.domain.model.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderLineItemPriceTest {
    @DisplayName("EatInOrderLineItemPrice를 생성한다.")
    @Test
    void create() {
        // given
        final long price = 1L;

        // when
        final EatInOrderLineItemPrice eatInOrderLineItemPrice = EatInOrderLineItemPrice.of(price);

        // then
        assertAll(
                () -> assertThat(eatInOrderLineItemPrice).isNotNull(),
                () -> assertThat(eatInOrderLineItemPrice.value()).isEqualTo(price)
        );
    }

    @DisplayName("EatInOrderLineItemPrice를 생성할 때 가격이 0 미만인 경우 예외를 던진다.")
    @Test
    void createWithNegativeValue() {
        // given
        final long price = -1L;

        // when
        final Throwable thrown = catchThrowable(() -> EatInOrderLineItemPrice.of(price));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0원 이상이어야 합니다.");
    }
}