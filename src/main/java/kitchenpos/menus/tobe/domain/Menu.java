package kitchenpos.menus.tobe.domain;

import java.util.UUID;

import kitchenpos.menus.tobe.domain.vo.DisplayedName;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;

public class Menu {

    private final UUID id;

    private final DisplayedName displayedName;

    private final MenuPrice price;

    private final MenuGroup menuGroup;
    private final MenuProducts menuProducts;
    private boolean displayed;

    public Menu(final UUID id,
                final DisplayedName displayedName,
                final MenuPrice price,
                final boolean displayed,
                final MenuGroup menuGroup,
                final MenuProducts menuProducts) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
        this.displayed = displayed;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
        checkDisplayPossible(price, menuProducts);
    }

    private void checkDisplayPossible(final MenuPrice price, final MenuProducts menuProducts) {
        final int menuProductsPriceSum = menuProducts.sum();

        if (price.isGraterThan(menuProductsPriceSum)) {
            this.displayed = false;
        }

    }

    public boolean isDisplayed() {
        return displayed;
    }
}
