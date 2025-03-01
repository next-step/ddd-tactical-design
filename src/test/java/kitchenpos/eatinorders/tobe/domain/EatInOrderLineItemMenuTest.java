package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItemMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("주문 항목 메뉴 테스트")
class EatInOrderLineItemMenuTest {

    @DisplayName("식별자가 주문 항목 메뉴의 메뉴 식별자와 일치하면 true 를 반환한다.")
    @Test
    void isSameMenu() {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", 16_000);

        final boolean actual = eatInOrderLineItemMenu.isSameMenu(menuId);

        assertThat(actual).isTrue();
    }

    @DisplayName("식별자가 주문 항목 메뉴의 메뉴 식별자와 불일치하면 false 를 반환한다.")
    @Test
    void isNotSameMenu() {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", 16_000);

        final boolean actual = eatInOrderLineItemMenu.isSameMenu(UUID.randomUUID());

        assertThat(actual).isFalse();
    }

    @DisplayName("가격이 주문 항목 메뉴의 가격과 일치하면 true 를 반환한다.")
    @CsvSource(value = {"1000:1000", "10_000:10_000", "16_000:16_000"}, delimiter = ':')
    @ParameterizedTest(name = "가격: {0}, 주문 항목 메뉴 가격: {1}")
    void isSamePriceValue(final int menuPrice, final int expectedPrice) {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", menuPrice);

        final boolean actual = eatInOrderLineItemMenu.isSamePrice(expectedPrice);

        assertThat(actual).isTrue();
    }

    @DisplayName("가격이 주문 항목 메뉴의 가격과 불일치하면 false 를 반환한다.")
    @CsvSource(value = {"1000:1001", "10_000:9_999", "0:1"}, delimiter = ':')
    @ParameterizedTest(name = "가격: {0}, 주문 항목 메뉴 가격: {1}")
    void isNotSamePriceValue(final int menuPrice, final int expectedPrice) {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", menuPrice);

        final boolean actual = eatInOrderLineItemMenu.isSamePrice(expectedPrice);

        assertThat(actual).isFalse();
    }

    @DisplayName("주문 항목 메뉴의 식별자를 반환한다.")
    @Test
    void menuIdValue() {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", 16_000);

        final UUID actual = eatInOrderLineItemMenu.menuId();

        assertThat(actual).isEqualTo(menuId);
    }
}
