package kitchenpos.menus.tobe.domain;

import java.util.List;

public class Menus {
    private final List<Menu> menus;

    public Menus(List<Menu> menus) {
        this.menus = menus;
    }

    public void hideMenuIfPriceExceedsSum() {
        for (final Menu menu : menus) {
            if (menu.exceedsSum(menu.getBigDecimalPrice())) {
                menu.hide();
            }
        }
    }
}
