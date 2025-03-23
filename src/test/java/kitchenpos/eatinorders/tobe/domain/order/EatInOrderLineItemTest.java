package kitchenpos.eatinorders.tobe.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    void 주문항목_생성시_메뉴_식별자가_존재해야한다() {
        // given & when & then
        assertThatThrownBy(() ->
                new EatInOrderLineItem(null, 1, 20_000)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문항목_생성시_가격이_0보다_작으면_안된다() {
        // given & when & then
        assertThatThrownBy(() ->
                new EatInOrderLineItem(UUID.randomUUID(), 1, -1)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
