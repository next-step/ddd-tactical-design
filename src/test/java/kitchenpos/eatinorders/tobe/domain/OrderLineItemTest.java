package kitchenpos.eatinorders.tobe.domain;

import java.util.Collections;
import kitchenpos.menus.tobe.domain.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.TobeFixtures.newMenu;
import static kitchenpos.TobeFixtures.newMenuGroup;
import static kitchenpos.TobeFixtures.newMenuProduct;
import static kitchenpos.TobeFixtures.newOrderLineItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class OrderLineItemTest {

    @Test
    @DisplayName("메뉴가 유효하지 않으면 주문 메뉴 생성 불가능")
    void createOrderLineItemFail01() {
        assertThatExceptionOfType(InvalidOrderLineException.class)
            .isThrownBy(() -> OrderLineItem.create(null, new Quantity(1L)));
    }

    @Test
    @DisplayName("메뉴가 비전시 상태면 주문 메뉴 생성 불가능")
    void createOrderLineItemFail02() {
        Menu menu = newMenu("메뉴", 1000L, newMenuGroup("메뉴그룹"), Collections.singletonList(newMenuProduct("메뉴상품", 1000L)));
        menu.hideMenu();

        assertThatExceptionOfType(InvalidOrderLineException.class)
            .isThrownBy(() -> OrderLineItem.create(menu, 5L));
    }

    @Test
    @DisplayName("주문 메뉴 생성 성공")
    void createOrderLineSuccess() {
        Menu menu = newMenu("메뉴", 1000L, newMenuGroup("메뉴그룹"), Collections.singletonList(newMenuProduct("메뉴상품", 1000L)));
        assertThatCode(() -> OrderLineItem.create(menu, new Quantity(5L)))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주문 메뉴의 가격은 메뉴의 가격과 재고의 곱을 반환")
    void getOrderLinePriceTest() {
        // given
        OrderLineItem item = newOrderLineItem("name", 1000L, 2L);

        // when
        long actual = item.getOrderLinePrice();

        // then
        assertThat(item.getPrice()).isEqualTo(item.getPrice());
        assertThat(item.getQuantity()).isEqualTo(item.getQuantity());
        assertThat(item.getOrderLinePrice()).isEqualTo(item.getPrice() * item.getQuantity());
    }
}
