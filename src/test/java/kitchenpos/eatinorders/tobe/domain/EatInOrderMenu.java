package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public interface EatInOrderMenu {
    boolean isSameMenu(UUID uuid);

    boolean isSamePrice(int price);

    boolean isDisplayed();
}
