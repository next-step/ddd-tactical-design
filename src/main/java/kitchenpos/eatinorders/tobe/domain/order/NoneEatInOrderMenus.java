package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

public class NoneEatInOrderMenus implements EatInOrderMenus {
    @Override
    public boolean isSameSize(final int size) {
        return true;
    }

    @Override
    public boolean isDisplayed(final UUID menuId) {
        return true;
    }

    @Override
    public boolean isSamePrice(final UUID menuId, final int menuPrice) {
        return true;
    }
}
