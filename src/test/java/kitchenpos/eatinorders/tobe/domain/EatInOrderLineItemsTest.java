package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.order.menu.DefaultEatInOrderMenu;
import kitchenpos.eatinorders.tobe.domain.order.menu.DefaultEatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.order.menu.EatInOrderMenus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("주문 항목 목록 테스트")
class EatInOrderLineItemsTest {

    @DisplayName("주문 항목의 개수를 반환한다.")
    @Test
    void size() {
        final UUID firstMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드치킨", 16_000, 1);

        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "후라이드치킨", 16_000, 1);

        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final int actual = eatInOrderLineItems.size();

        assertThat(actual).isEqualTo(2);
    }

    @DisplayName("주문 항목이 없거나 비어있으면 매장 주문 항목 목록을 생성할 수 없다.")
    @ParameterizedTest(name = "주문 항목: {0}")
    @NullAndEmptySource
    void createWithoutEatInOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItemList) {
        assertThatThrownBy(() -> new EatInOrderLineItems(eatInOrderLineItemList))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목 메뉴와 메뉴의 개수가 일치하지 않으면 매장 주문을 생성할 수 없다.")
    @Test
    void createWithDifferentMenuSize() {
        final UUID firstMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드치킨", 16_000, 1);

        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념치킨", 16_000, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(new DefaultEatInOrderMenu(firstMenuId, 16_001, true));

        assertThatThrownBy(() -> eatInOrderLineItems.ensureIntegrity(eatInOrderMenus))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목 메뉴와 메뉴의 가격이 일치하지 않으면 매장 주문을 생성할 수 없다.")
    @Test
    void createWithDifferentMenuPrice() {
        final UUID firstMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드치킨", 16_000, 1);

        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념치킨", 16_000, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_001, true)
        );

        assertThatThrownBy(() -> eatInOrderLineItems.ensureIntegrity(eatInOrderMenus))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
