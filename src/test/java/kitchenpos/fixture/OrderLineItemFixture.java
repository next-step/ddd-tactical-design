package kitchenpos.fixture;

import static kitchenpos.fixture.MenuFixture.MENU1;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderQuantity;
import kitchenpos.menus.tobe.domain.model.MenuPrice;

public class OrderLineItemFixture {

    private static final UUID menuId = MENU1().getId();

    public static OrderLineItem ORDER_LINE_ITEM1() {
        return new OrderLineItem(menuId, new MenuPrice(16000L), new OrderQuantity(3));
    }

}
