package kitchenpos.menus.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToBeMenus {
    private final List<ToBeMenu> value;

    public ToBeMenus(List<ToBeMenu> value) {
        if (value == null || value.isEmpty()) {
            value = new ArrayList<>();
        }
        this.value = value;
    }

    public boolean hasHiddenMenu() {
        return value.stream()
            .anyMatch(ToBeMenu::isHide);
    }

    public boolean isNotMatchByMenuAndPrice(UUID menuId, BigDecimal price) {
        return value.stream()
            .noneMatch(it -> it.isSameMenuAndPrice(menuId, price));
    }
}
