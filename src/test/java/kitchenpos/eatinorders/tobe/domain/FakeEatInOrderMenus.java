package kitchenpos.eatinorders.tobe.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FakeEatInOrderMenus implements EatInOrderMenus {

    private final Map<UUID, EatInOrderMenu> eatInOrderMenus;

    public FakeEatInOrderMenus() {
        this(new HashMap<>());
    }

    public FakeEatInOrderMenus(final Map<UUID, EatInOrderMenu> eatInOrderMenus) {
        this.eatInOrderMenus = eatInOrderMenus;
    }

    @Override
    public boolean isSameSize(final int size) {
        return eatInOrderMenus.size() == size;
    }

    @Override
    public boolean isDisplayed(final UUID menuId) {
        return eatInOrderMenus.get(menuId).isDisplayed();
    }

    @Override
    public boolean isSamePrice(final UUID menuId, final int menuPrice) {
        return eatInOrderMenus.get(menuId).isSamePrice(menuPrice);
    }
}
