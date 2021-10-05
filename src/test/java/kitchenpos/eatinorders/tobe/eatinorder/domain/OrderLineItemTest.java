package kitchenpos.eatinorders.tobe.eatinorder.domain;

import kitchenpos.menus.tobe.menu.domain.MenuId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("주문 상품(OrderLineItem)은")
class OrderLineItemTest {
    private static final MenuId MENU_ID = new MenuId(UUID.randomUUID());
    private static final Price PRICE = new Price(1000L);
    private static final Quantity QUANTITY = new Quantity(1L);


    @DisplayName("생성할 수 있다.")
    @Test
    void create() {
        final OrderLineItem orderLineItem = new OrderLineItem(MENU_ID, QUANTITY, PRICE);
        assertThat(orderLineItem.getMenuId()).isNotNull();
        assertThat(orderLineItem).isEqualTo(new OrderLineItem(MENU_ID, QUANTITY, PRICE));
    }

}
