package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("주문 항목 목록 테스트")
class EatInOrderLineItemsTest {

    @DisplayName("메뉴의 가격과 주문 항목 메뉴의 가격이 일치하면 true 를 반환한다.")
    @CsvSource(value = {"1000:1000", "10_000:10_000", "16_000:16_000"}, delimiter = ':')
    @ParameterizedTest(name = "{index}. 메뉴 가격: {0}, 주문 항목 메뉴 가격: {1}")
    void isSamePrice(final int menuPrice, final int expectedPrice) {
        // given
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", menuPrice);
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(1L, eatInOrderLineItemMenu, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(eatInOrderLineItem));

        // when
        assertThat(eatInOrderLineItems.isSamePrice(menuId, expectedPrice)).isTrue();
    }
}
