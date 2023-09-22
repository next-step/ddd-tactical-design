package kitchenpos.eatinorders.domain.orders;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OrderedMenus {
    private final List<OrderedMenu> orderedMenus;

    public OrderedMenus(List<OrderedMenu> orderedMenus) {
        this.orderedMenus = orderedMenus;
    }

    public boolean containsMenuId(UUID menuId) {
        return orderedMenus.stream()
                .anyMatch(orderedMenu -> orderedMenu.isSameMenuId(menuId));
    }

    public OrderedMenu getOrderedMenuByMenuId(UUID menuId) {
        return this.orderedMenus.stream()
                .filter(orderedMenu -> orderedMenu.isSameMenuId(menuId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("주문된 메뉴가 존재하지 않습니다. menuId: " + menuId));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedMenus that = (OrderedMenus) o;
        return Objects.equals(orderedMenus, that.orderedMenus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderedMenus);
    }
}
