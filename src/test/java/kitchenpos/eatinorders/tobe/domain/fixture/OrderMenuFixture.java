package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.OrderMenu;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderMenuFixture {

    public static OrderMenu ORDER_MENU_WITH_MENU_ID_AND_PRICE(final UUID menuId, final Price price) {
        return new OrderMenu(UUID.randomUUID(), menuId, price, true);
    }

    public static OrderMenu ORDER_MENU_WITH_PRICE(final Price price) {
        return ORDER_MENU_WITH_MENU_ID_AND_PRICE(UUID.randomUUID(), price);
    }

    public static OrderMenu NOT_DISPLAYED_ORDER_MENU() {
        return new OrderMenu(UUID.randomUUID(), UUID.randomUUID(), new Price(BigDecimal.valueOf(16_000L)), false);
    }
}
