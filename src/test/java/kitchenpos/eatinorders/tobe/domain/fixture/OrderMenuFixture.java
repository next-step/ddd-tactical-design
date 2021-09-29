package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.OrderMenu;

import java.util.UUID;

public class OrderMenuFixture {

    public static OrderMenu ORDER_MENU_WITH_MENU_ID_AND_PRICE(final UUID menuId, final Price price) {
        return new OrderMenu(menuId, price, true);
    }
}
