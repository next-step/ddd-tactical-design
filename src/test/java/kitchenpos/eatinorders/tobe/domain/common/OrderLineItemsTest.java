package kitchenpos.eatinorders.tobe.domain.common;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.MenuId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLineItemsTest {

    @DisplayName("주문 항목은 입력하지 않으면 예외가 발생한다")
    @NullAndEmptySource
    @ParameterizedTest
    void create(List<OrderLineItem> orderLineItems) {
        assertThatThrownBy(() -> new OrderLineItems(orderLineItems))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목의 갯수를 알 수 있다")
    @Test
    void size() {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000)),
                new OrderLineItem(2L, MenuId.generate(), 1, new Price(20_000))
        );

        int size = orderLineItems.size();

        assertThat(size).isEqualTo(2);
    }
    
    @DisplayName("주문 총 액을 알 수 있다")
    @Test
    void totalPrice(){
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000)),
                new OrderLineItem(2L, MenuId.generate(), 1, new Price(20_000))
        );

        Price totalPrice = orderLineItems.totalPrice();

        assertThat(totalPrice).isEqualTo(new Price(30_000));
    }

    @DisplayName("주문 항목의 메뉴 아이디 리스트를 조회할 수 있다")
    @Test
    void menuIds(){
        MenuId id1 = MenuId.generate();
        MenuId id2 = MenuId.generate();
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, id1, 1, new Price(10_000)),
                new OrderLineItem(2L, id2, 1, new Price(20_000))
        );

        List<MenuId> menuIds = orderLineItems.menuIds();

        assertThat(menuIds).hasSize(2);
        assertThat(menuIds).containsExactly(id1, id2);
    }

    @DisplayName("주문 항목의 갯수가 맞는지 확인한다")
    @Test
    void isSizeMismatch(){
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000)),
                new OrderLineItem(2L, MenuId.generate(), 1, new Price(20_000))
        );

        boolean result1 = orderLineItems.isSizeMismatch(1);
        boolean result2 = orderLineItems.isSizeMismatch(2);

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }
    
    @DisplayName("주문 항목 중 실제 메뉴의 가격과 다른 메뉴가 있는지 확인한다")
    @Test
    void hasDifferentPrice(){
        MenuId id1 = MenuId.generate();
        MenuId id2 = MenuId.generate();
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, id1, 1, new Price(10_000)),
                new OrderLineItem(2L, id2, 1, new Price(20_000))
        );

        boolean result1 = orderLineItems.hasDifferentPrice(id1, new Price(20_000));
        boolean result2 = orderLineItems.hasDifferentPrice(id2, new Price(20_000));

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }
}
