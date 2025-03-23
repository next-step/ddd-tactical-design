package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenu;

import java.util.UUID;

public class DefaultEatInOrderMenu implements EatInOrderMenu {

    public boolean exists(final UUID menuID) {
        return false;
    }

    public boolean isDisplayed(final UUID menuId) {
        return false;
    }

    public boolean isSamePrice(final UUID menuId, final int price) {
        return false;
    }
}
