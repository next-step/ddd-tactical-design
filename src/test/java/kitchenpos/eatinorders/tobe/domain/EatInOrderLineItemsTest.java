package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.NewFixtures;
import kitchenpos.eatinorders.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.EatInOrderLineItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.ORDER_LINE_ITEM_EMPTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("주문아이템목록 테스트")
class EatInOrderLineItemsTest {

    @DisplayName("주문아이템목록이 없으면 생성할 수 없다.")
    @Test
    void create_failed() {
        assertThatThrownBy(() -> EatInOrderLineItems.create(Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ORDER_LINE_ITEM_EMPTY);
    }

    @DisplayName("주문아이템목록 생성 성공")
    @Test
    void create() {
        EatInOrderLineItem eatInOrderLineItem = NewFixtures.eatInOrderLineItem();
        EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.create(List.of(eatInOrderLineItem));

        assertThat(eatInOrderLineItems).isEqualTo(EatInOrderLineItems.create(List.of(eatInOrderLineItem)));
    }

}