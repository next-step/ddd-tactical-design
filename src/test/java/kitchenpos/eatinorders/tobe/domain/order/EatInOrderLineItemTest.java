package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidEatInOrderLineItemException;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItems;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
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
        final UUID actual = eatInOrderLineItem.getMenuId();

        // then
        assertThat(actual).isEqualTo(menuId);
    }

    @Test
    void 주문항목_생성시_메뉴_식별자가_존재해야한다() {
        // given & when & then
        assertThatThrownBy(() ->
                new EatInOrderLineItem(null, 1, 20_000)
        ).isInstanceOf(InvalidEatInOrderLineItemException.class);
    }

    @ParameterizedTest(name = "주문 항목: {0}")
    @NullAndEmptySource
    void 주문항목이_없거나_비어있으면_매장주문_항목_목록을_생성할_수_없다(final List<EatInOrderLineItem> eatInOrderLineItems) {
        assertThatThrownBy(() -> new EatInOrderLineItems(new NoneEatInOrderMenus(), eatInOrderLineItems))
                .isExactlyInstanceOf(InvalidEatInOrderLineItemException.class)
                .hasMessage("주문 항목이 존재해야 합니다.");
    }
}
