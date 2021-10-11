package kitchenpos.menus.tobe.fixture;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.Price;

import java.util.UUID;

public class MenuRequest {
    private DisplayedName name;

    private Price price;

    private boolean displayed;

    private MenuGroup menuGroup;

    private MenuProducts menuProducts;

    public MenuRequest(
            final DisplayedName name,
            final Price price,
            final boolean displayed,
            final MenuGroup menuGroup,
            final MenuProducts menuProducts
    ) {
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
    }

    public DisplayedName getName() {
        return name;
    }

    public void setName(final DisplayedName name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(final Price price) {
        this.price = price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(final MenuProducts menuProducts) {
        this.menuProducts = menuProducts;
    }
}
