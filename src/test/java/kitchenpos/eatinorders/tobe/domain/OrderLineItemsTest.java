package kitchenpos.eatinorders.tobe.domain;

import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static kitchenpos.TobeFixtures.newOrderLineItem;
import static kitchenpos.TobeFixtures.newOrderLineItems;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OrderLineItemsTest {

    @Test
    @DisplayName("주문 메뉴가 비어 있으면(null) 생성 불가능")
    void createOrderLineItemFail01() {
        assertThatIllegalArgumentException().isThrownBy(() -> new OrderLineItems(null));
    }

    @Test
    @DisplayName("주문 메뉴가 비어 있으면(size 0) 생성 불가능")
    void createOrderLineItemFail02() {
        assertThatIllegalArgumentException().isThrownBy(() -> new OrderLineItems(Collections.emptyList()));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주문 메뉴 중 재고가 0개 미만인 메뉴가 있으면 true, 전부 0개 이상이면 false 반환")
    void hasNegativeQuantityTest(long value, boolean expected) {
        // given
        OrderLineItem item1 = newOrderLineItem("name", 1000L, 1L);
        OrderLineItem item2 = newOrderLineItem("name", 2000L, 2L);
        OrderLineItem target = newOrderLineItem("name", 2000L, value);

        OrderLineItems sut = newOrderLineItems(item1, item2, target);

        // when
        boolean actual = sut.hasNegativeQuantity();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> hasNegativeQuantityTest() {
        return Stream.of(
            Arguments.of(0L, false),
            Arguments.of(1L, false),
            Arguments.of(-1L, true),
            Arguments.of(-5L, true)
        );
    }

    @Test
    @DisplayName("주문 메뉴 가격의 합은 메뉴 가격과 재고를 곱한 수의 합을 반환")
    void sumOfMenuPricesTest() {
        // given
        OrderLineItem item1 = newOrderLineItem("name", 1000L, 1L);
        OrderLineItem item2 = newOrderLineItem("name", 2000L, 2L);
        OrderLineItem item3 = newOrderLineItem("name", 3000L, 3L);

        OrderLineItems orderLineItems = newOrderLineItems(item1, item2, item3);

        // when
        long actual = orderLineItems.sumOfMenuPrices();

        // then
        assertThat(actual).isEqualTo(item1.getOrderLinePrice() + item2.getOrderLinePrice() + item3.getOrderLinePrice());
    }
}
