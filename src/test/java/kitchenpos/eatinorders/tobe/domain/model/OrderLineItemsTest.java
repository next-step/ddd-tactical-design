package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static kitchenpos.eatinorders.tobe.domain.fixture.OrderLineItemFixture.DEFAULT_ORDER_LINE_ITEM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLineItemsTest {

    @DisplayName("주문 항목 목록을 생성한다.")
    @Test
    void 생성_성공() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();

        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));

        assertThat(orderLineItems.getMenuIds()).contains(orderLineItem.getMenuId());
    }

    @DisplayName("주문 항목 목록은 1개 이상의 주문 항목이 필수다.")
    @Test
    void 생성_실패() {
        ThrowableAssert.ThrowingCallable when = () -> new OrderLineItems(Collections.emptyList());

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목은 1개 이상이어야 합니다.");
    }

    @DisplayName("주문 항목 목록은 주문 항목의 금액의 합을 반환한다.")
    @Test
    void 메뉴_식별자_목록_반환_성공() {
        final OrderLineItem orderLineItem1 = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItem orderLineItem2 = DEFAULT_ORDER_LINE_ITEM();

        final OrderLineItems orderLineItems = new OrderLineItems(Arrays.asList(orderLineItem1, orderLineItem2));

        assertThat(orderLineItems.getMenuIds()).contains(orderLineItem1.getMenuId(), orderLineItem2.getMenuId());
    }

    @DisplayName("주문 항목 목록은 메뉴 별 금액(가격 * 수량)의 합이 0보다 작은 주문 항목 있는지 확인하여 계산 완료 가능 여부를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideArguments")
    void 계산_완료_가능_여부_반환_성공(final List<OrderLineItem> orderLineItemList, final boolean expected) {
        final OrderLineItems orderLineItems = new OrderLineItems(orderLineItemList);

        assertThat(orderLineItems.canBeCompleted()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideArguments() {
        final Price price = new Price(BigDecimal.valueOf(16_000L));
        final UUID menuId1 = UUID.randomUUID();
        final UUID menuId2 = UUID.randomUUID();

        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                new OrderLineItem(UUID.randomUUID(), menuId1, price, 1L),
                                new OrderLineItem(UUID.randomUUID(), menuId1, price, -1L)
                        ),
                        true
                ),
                Arguments.of(
                        Arrays.asList(
                                new OrderLineItem(UUID.randomUUID(), menuId1, price, 1L),
                                new OrderLineItem(UUID.randomUUID(), menuId2, price, 1L),
                                new OrderLineItem(UUID.randomUUID(), menuId2, price, -2L)
                        ),
                        false
                )
        );
    }

    @DisplayName("주문 항목 목록은 주문 가격과 메뉴 가격이 같지 않은 주문 항목이 있으면, IllegalArgumentException을 던진다.")
    @Test
    void validateOrderPrice() {
        final UUID menuId = UUID.randomUUID();
        final OrderLineItem orderLineItem = new OrderLineItem(UUID.randomUUID(), menuId, new Price(20_000L), 1L);
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Map<UUID, Price> menuPriceMap = new HashMap<>();
        menuPriceMap.put(menuId, new Price(16_000L));

        ThrowableAssert.ThrowingCallable when = () -> orderLineItems.validateOrderPrice(menuPriceMap);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목의 가격과 메뉴 가격이 일치하지 않습니다.");
    }
}
