package kitchenpos.eatinorders.order.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderLineItemsTest {

    @Nested
    class CreateTest {

        @DisplayName("성공")
        @Test
        void success() {
            final EatInOrderLineItem eatInOrderLineItem1 = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);
            final EatInOrderLineItem eatInOrderLineItem2 = EatInOrderLineItem.create(UUID.randomUUID(), 25_000L, 1);

            final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItem1, eatInOrderLineItem2);

            assertAll(
                    () -> assertThat(eatInOrderLineItems).isNotNull(),
                    () -> assertThat(eatInOrderLineItems.values().size()).isEqualTo(2)
            );
        }

        @DisplayName("주문상품이 한개 이상이여야 한다.")
        @Test
        void error() {
            assertThatThrownBy(() -> EatInOrderLineItems.of(Collections.emptyList()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("주문상품은 1개 이상이여야 합니다.");
        }
    }
}
