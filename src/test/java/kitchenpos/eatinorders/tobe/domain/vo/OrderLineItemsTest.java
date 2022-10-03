package kitchenpos.eatinorders.tobe.domain.vo;

import static java.util.Collections.emptyList;
import static java.util.List.of;
import static kitchenpos.eatinorders.tobe.OrderFixtures.menu;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderLineItem;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemsTest {

    private DisplayedMenu menu;

    @BeforeEach
    void setUp() {
        menu = menu("양념치킨", 16_000);
    }

    @DisplayName("주문 항목 일급 컬렉션을 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new OrderLineItems(of(
                orderLineItem(1, menu)
        ))).doesNotThrowAnyException();
    }

    @DisplayName("반드시 하나 이상의 주문 항목을 갖는다.")
    @Test
    void error1() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new OrderLineItems(emptyList()));
    }
}
