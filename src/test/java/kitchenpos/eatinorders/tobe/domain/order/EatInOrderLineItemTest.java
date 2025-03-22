package kitchenpos.eatinorders.tobe.domain.order;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@DisplayName("주문 항목에 대한 테스트")
class EatInOrderLineItemTest {

    @Test
    void 주문_항목_메뉴의_식별자를_조회한다() {
        // given
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItem eatInOrderLineItem
                = new EatInOrderLineItem(menuId, 1, 20_000);

        // when
        final UUID actual = eatInOrderLineItem.menuId();

        // then
        assertThat(actual).isEqualTo(menuId);
     }

}
