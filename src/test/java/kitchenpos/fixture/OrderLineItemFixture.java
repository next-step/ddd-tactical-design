package kitchenpos.fixture;

import static kitchenpos.fixture.MenuFixture.MENU1;

import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderQuantity;
import kitchenpos.menus.tobe.menu.domain.model.MenuPrice;

public class OrderLineItemFixture {

    public static OrderLineItem ORDER_LINE_ITEM1() {
        return new OrderLineItem(MENU1().getId(), new MenuPrice(16000L), new OrderQuantity(3));
    }

}
