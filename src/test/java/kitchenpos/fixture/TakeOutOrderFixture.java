package kitchenpos.fixture;

import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.eatinorder.domain.Price;
import kitchenpos.eatinorders.tobe.eatinorder.domain.Quantity;
import kitchenpos.menus.tobe.menu.domain.Menu;
import kitchenpos.menus.tobe.menu.domain.MenuId;

import java.util.UUID;

public class TakeOutOrderFixture {

    public static OrderLineItem 주문상품(final UUID menuId, final long price) {
        return new OrderLineItem(new MenuId(menuId), new Quantity(1L), new Price(price));
    }

    public static OrderLineItem 주문상품(final Menu menu, final long price) {
        return new OrderLineItem(new MenuId(menu.getId()), new Quantity(1L), new Price(price));
    }
}
