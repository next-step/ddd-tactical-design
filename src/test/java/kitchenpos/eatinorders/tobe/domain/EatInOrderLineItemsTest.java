package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItemMenu;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("주문 항목 목록 테스트")
class EatInOrderLineItemsTest {

    @DisplayName("메뉴의 가격과 주문 항목 메뉴의 가격이 일치하면 true 를 반환한다.")
    @CsvSource(value = {"1000:1000", "10_000:10_000", "16_000:16_000"}, delimiter = ':')
    @ParameterizedTest(name = "{index}. 메뉴 가격: {0}, 주문 항목 메뉴 가격: {1}")
    void isSamePrice(final int menuPrice, final int expectedPrice) {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", menuPrice);
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(eatInOrderLineItemMenu, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(eatInOrderLineItem));

        assertThat(eatInOrderLineItems.isSamePrice(menuId, expectedPrice)).isTrue();
    }

    @DisplayName("메뉴의 가격과 주문 항목 메뉴의 가격이 불일치하면 false 를 반환한다.")
    @CsvSource(value = {"1000:1001", "10_000:9_999", "0:1"}, delimiter = ':')
    @ParameterizedTest(name = "{index}. 메뉴 가격: {0}, 주문 항목 메뉴 가격: {1}")
    void isNotSamePrice(final int menuPrice, final int expectedPrice) {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", menuPrice);
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(eatInOrderLineItemMenu, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(eatInOrderLineItem));

        assertThat(eatInOrderLineItems.isSamePrice(menuId, expectedPrice)).isFalse();
    }

    @DisplayName("메뉴의 가격을 비교할 주문 항목의 메뉴가 없으면 예외를 발생한다.")
    @CsvSource(value = {"1000", "10_000", "16_000"}, delimiter = ':')
    @ParameterizedTest(name = "메뉴 가격: {0}")
    void isSamePriceWithEmptyMenu(final int menuPrice) {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", menuPrice);
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(eatInOrderLineItemMenu, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(eatInOrderLineItem));

        assertThatThrownBy(() -> eatInOrderLineItems.isSamePrice(UUID.randomUUID(), menuPrice))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 식별자 목록을 반환한다.")
    @Test
    void menuIds() {
        final UUID firstMenuId = UUID.randomUUID();
        final EatInOrderLineItemMenu firstEatInOrderLineItemMenu = new EatInOrderLineItemMenu(firstMenuId, "후라이드치킨", 16_000);
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstEatInOrderLineItemMenu, 1);

        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItemMenu secondEatInOrderLineItemMenu = new EatInOrderLineItemMenu(secondMenuId, "후라이드치킨", 16_000);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondEatInOrderLineItemMenu, 1);

        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(firstEatInOrderLineItem, secondEatInOrderLineItem));
        final List<UUID> actual = eatInOrderLineItems.menuIds();

        assertThat(actual).containsExactlyInAnyOrder(firstMenuId, secondMenuId);
    }

    @DisplayName("주문 항목의 개수를 반환한다.")
    @Test
    void size() {
        final UUID firstMenuId = UUID.randomUUID();
        final EatInOrderLineItemMenu firstEatInOrderLineItemMenu = new EatInOrderLineItemMenu(firstMenuId, "후라이드치킨", 16_000);
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstEatInOrderLineItemMenu, 1);

        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItemMenu secondEatInOrderLineItemMenu = new EatInOrderLineItemMenu(secondMenuId, "후라이드치킨", 16_000);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondEatInOrderLineItemMenu, 1);

        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(firstEatInOrderLineItem, secondEatInOrderLineItem));
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
}
