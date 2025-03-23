package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

public interface EatInOrderMenus {

    boolean isSameSize(int size);
    boolean isDisplayed(UUID menuId);
    boolean isSamePrice(UUID menuId, int price);
}
