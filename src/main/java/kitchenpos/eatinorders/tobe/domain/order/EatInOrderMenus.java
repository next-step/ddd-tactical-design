package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

public interface EatInOrderMenus {

    boolean isSameSize(final int size);
    boolean isDisplayed(final UUID menuId);
    boolean isSamePrice(final UUID menuId, int price);
}
