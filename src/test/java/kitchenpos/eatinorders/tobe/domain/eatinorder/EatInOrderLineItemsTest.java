package kitchenpos.eatinorders.tobe.domain.eatinorder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class EatInOrderLineItemsTest {

    @DisplayName("매장 식사 주문 항목 리스트를 생성할 수 있다.")
    @Test
    void create() {
        // given
        final long seq = 1L;
        final long quantity = 1;
        final UUID menuId = UUID.randomUUID();

        List<EatInOrderLineItem> eatInOrderLineItemList = List.of(
            new EatInOrderLineItem(seq, menuId, quantity),
            new EatInOrderLineItem((seq + 1), menuId, quantity)
        );

        // when
        EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(eatInOrderLineItemList);

        // then
        assertThat(eatInOrderLineItems).isNotNull();
        assertThat(eatInOrderLineItems.getValue()).hasSize(2);
        assertThat(eatInOrderLineItems.getValue()).containsExactlyElementsOf(
            eatInOrderLineItemList);

        eatInOrderLineItems.getValue().forEach(menuProduct -> {
            assertThat(menuProduct.getMenuId()).isEqualTo(menuId);
            assertThat(menuProduct.getQuantity()).isEqualTo(quantity);
        });
    }

    @DisplayName("최소 개수 미만의 매장 식사 주문 항목 리스트를 생성 시, 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createWithNull(List<EatInOrderLineItem> value) {
        // when then
        assertThatThrownBy(() -> new EatInOrderLineItems(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(EatInOrderLineItems.ERROR_MESSAGE_EMPTY);
    }
}
