package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("주문 항목 테스트")
class EatInOrderLineItemTest {

    @DisplayName("주문 항목 메뉴의 식별자를 반환한다.")
    @Test
    void menuIdValue() {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(
                menuId, "후라이드치킨", 16_000, 1
        );

        final UUID actual = eatInOrderLineItem.menuId();

        assertThat(actual).isEqualTo(menuId);
    }
}
