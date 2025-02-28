package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;

public class EatInOrderMenus {
    private final List<EatInOrderMenu> eatInOrderMenus;

    public EatInOrderMenus(final List<EatInOrderMenu> eatInOrderMenus) {
        this.eatInOrderMenus = eatInOrderMenus;
    }

    public int size() {
        return eatInOrderMenus.size();
    }

    public boolean isSamePrice(final UUID uuid, final int price) {
        return eatInOrderMenus.stream()
                .filter(eatInOrderMenu -> eatInOrderMenu.isSameMenu(uuid))
                .findFirst()
                .map(eatInOrderMenu -> eatInOrderMenu.isSamePrice(price))
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isDisplayed(final UUID uuid) {
        return eatInOrderMenus.stream()
                .filter(eatInOrderMenu -> eatInOrderMenu.isSameMenu(uuid))
                .findFirst()
                .map(EatInOrderMenu::isDisplayed)
                .orElseThrow(IllegalArgumentException::new);
    }
}
