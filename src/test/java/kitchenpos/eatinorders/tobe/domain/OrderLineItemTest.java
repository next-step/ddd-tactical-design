package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.eatinorders.tobe.OrderFixtures.menu;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderLineItem;
import static kitchenpos.eatinorders.tobe.OrderFixtures.price;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import kitchenpos.eatinorders.tobe.domain.vo.DisplayedMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemTest {

    private DisplayedMenu menu;

    @BeforeEach
    void setUp() {
        menu = menu("앙념치킨", 16_000);
    }

    @DisplayName("매장 주문 항목을 생성할 수 있다.")
    @Test
    void create() {
        assertThatCode(() -> orderLineItem(0, menu))
                .doesNotThrowAnyException();
    }

    @DisplayName("매장 주문 항목은 0개 이상을 수량은 갖는다.")
    @Test
    void error1() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderLineItem(-1, menu));
    }

    @DisplayName("매장 주문 항목 가격과 지정된 가격이 같은지 검증한다.")
    @Test
    void samePrice() {
        OrderLineItem expected = orderLineItem(1, menu);

        assertThat(expected.samePrice(menu.getPrice())).isTrue();
        assertThat(expected.samePrice(menu.getPrice().add(price(1)))).isFalse();
    }
}
