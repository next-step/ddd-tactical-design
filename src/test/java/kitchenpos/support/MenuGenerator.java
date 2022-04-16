package kitchenpos.support;

import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuGenerator {

    public static MenuProduct createMenuProduct(long seq, long quantity, BigDecimal price) {
        return new MenuProduct(seq, quantity, UUID.randomUUID(), price);
    }

    public static MenuGroup createMenuGroup(String menuGroupName) {
        return new MenuGroup(menuGroupName, new StubBanWordFilter(false));
    }
}
