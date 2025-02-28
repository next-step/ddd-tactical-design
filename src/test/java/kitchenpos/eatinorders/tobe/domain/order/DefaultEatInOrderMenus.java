package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.UUID;

public class DefaultEatInOrderMenus implements EatInOrderMenus {
    private final List<EatInOrderMenu> defaultEatInOrderMenus;

    public DefaultEatInOrderMenus(final EatInOrderMenu... defaultEatInOrderMenus) {
        this(List.of(defaultEatInOrderMenus));
    }

    public DefaultEatInOrderMenus(final List<EatInOrderMenu> defaultEatInOrderMenus) {
        this.defaultEatInOrderMenus = defaultEatInOrderMenus;
    }

    public boolean isSameSize(final int size) {
        return defaultEatInOrderMenus.size() == size;
    }

    public boolean isSamePrice(final UUID uuid, final int price) {
        return defaultEatInOrderMenus.stream()
                .filter(defaultEatInOrderMenu -> defaultEatInOrderMenu.isSameMenu(uuid))
                .findFirst()
                .map(defaultEatInOrderMenu -> defaultEatInOrderMenu.isSamePrice(price))
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isDisplayed(final UUID uuid) {
        return defaultEatInOrderMenus.stream()
                .filter(defaultEatInOrderMenu -> defaultEatInOrderMenu.isSameMenu(uuid))
                .findFirst()
                .map(EatInOrderMenu::isDisplayed)
                .orElseThrow(IllegalArgumentException::new);
    }
}
