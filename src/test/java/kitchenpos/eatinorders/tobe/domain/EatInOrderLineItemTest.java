package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.NewFixtures;
import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.domain.EatInMenu;
import kitchenpos.eatinorders.domain.EatInOrderLineItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.NOT_EQUALS_PRICE;
import static kitchenpos.eatinorders.exception.OrderExceptionMessage.ORDER_LINE_ITEM_MENU_NOT_DISPLAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("주문아이템 테스트")
class EatInOrderLineItemTest {

    @DisplayName("주문아이템 생성 성공")
    @Test
    void create() {
        EatInMenu eatInMenu = NewFixtures.eatInMenu(10_000L, true);

        EatInOrderLineItem eatInOrderLineItem = EatInOrderLineItem.create(eatInMenu, 1L, Price.of(BigDecimal.valueOf(10_000L)));

        assertThat(eatInOrderLineItem).isEqualTo(EatInOrderLineItem.create(eatInMenu, 1L, Price.of(BigDecimal.valueOf(10_000L))));
    }

    @DisplayName("숨겨진 메뉴는 주문아이템을 생성할 수 없다.")
    @Test
    void create_failed_hide() {
        EatInMenu eatInMenu = NewFixtures.eatInMenu(10_000L, false);

        assertThatThrownBy(() -> EatInOrderLineItem.create(1L, eatInMenu, 1L, Price.of(BigDecimal.valueOf(10_000L))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ORDER_LINE_ITEM_MENU_NOT_DISPLAY);
    }

    @DisplayName("메뉴의 가격과 주문아이템의 가격이 다르면 주문아이템을 생성할 수 없다.")
    @Test
    void create_failed_not_equal_price() {
        EatInMenu eatInMenu = NewFixtures.eatInMenu(10_000L, true);

        assertThatThrownBy(() -> EatInOrderLineItem.create(1L, eatInMenu, 1L, Price.of(BigDecimal.valueOf(20_000L))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_EQUALS_PRICE);
    }
}