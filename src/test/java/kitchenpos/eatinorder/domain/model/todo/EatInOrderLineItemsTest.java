package kitchenpos.eatinorder.domain.model.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderLineItemsTest {
    @DisplayName("EatInOrderLineItems를 생성한다.")
    @Test
    void create() {
        // given
        final EatInOrderLineItem eatInOrderLineItem = EatInOrderLineItem.of(1L, UUID.randomUUID(), 1L, 1L);

        // when
        final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(List.of(eatInOrderLineItem));

        // then
        assertAll(
                () -> assertThat(eatInOrderLineItems).isNotNull(),
                () -> assertThat(eatInOrderLineItems.getEatInOrderLineItems()).containsExactly(eatInOrderLineItem)
        );
    }

    @DisplayName("EatInOrderLineItems를 생성할 때 null이 포함된 경우 예외를 던진다.")
    @Test
    void createWithNull() {
        // given
        final List<EatInOrderLineItem> eatInOrderLineItems = null;

        // when
        final Throwable thrown = catchThrowable(() -> EatInOrderLineItems.of(eatInOrderLineItems));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("식사 주문 항목은 하나 이상 포함되어야 합니다.");
    }

    @DisplayName("EatInOrderLineItems를 생성할 때 비어있는 경우 예외를 던진다.")
    @Test
    void createWithEmpty() {
        // given
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of();

        // when
        final Throwable thrown = catchThrowable(() -> EatInOrderLineItems.of(eatInOrderLineItems));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("식사 주문 항목은 하나 이상 포함되어야 합니다.");
    }
}