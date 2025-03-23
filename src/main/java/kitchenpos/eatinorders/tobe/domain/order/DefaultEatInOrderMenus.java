package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.UUID;

public class DefaultEatInOrderMenus implements EatInOrderMenus {

    private final List<EatInOrderMenu> menus;

    public DefaultEatInOrderMenus(final EatInOrderMenu... menus) {
        this(List.of(menus));
    }

    public DefaultEatInOrderMenus(final List<EatInOrderMenu> menus) {
        this.menus = menus;
    }

    @Override
    public boolean isSameSize(final int size) {
        return menus.size() == size;
    }

    @Override
    public boolean isDisplayed(final UUID menuId) {
        return findBy(menuId).isDisplayed();
    }

    @Override
    public boolean isSamePrice(final UUID menuId, final int price) {
        return findBy(menuId).isSamePrice(price);
    }

    private EatInOrderMenu findBy(final UUID menuId) {
        return menus.stream()
                .filter(m -> m.getMenuId().equals(menuId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));
    }
}
