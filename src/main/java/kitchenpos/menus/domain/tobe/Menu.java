package kitchenpos.menus.domain.tobe;

import java.util.Objects;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.MenuGroup;

public class Menu {
    private MenuID id;
    private MenuName name;
    private MenuPrice price;
    private MenuGroup menuGroup;
    private boolean displayed;
    private MenuProducts menuProducts;

    public Menu(
        final MenuID menuID,
        final MenuName menuName,
        final MenuPrice menuPrice,
        final MenuGroup menuGroup,
        final boolean displayed,
        final MenuProducts menuProducts
    ) {
        this.id = menuID;
        this.name = menuName;
        priceLessThanMenuProductsPrice(menuPrice, menuProducts);
        this.price = menuPrice;
        if (Objects.isNull(menuGroup)) {
            throw new IllegalArgumentException("MenuGroup is required");
        }
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    private void priceLessThanMenuProductsPrice(final MenuPrice price, final MenuProducts menuProducts) {
        if (price.value().compareTo(menuProducts.totalPrice()) > 0) {
            throw new IllegalArgumentException("MenuPrice must less than MenuProducts price");
        }
    }

    public void changePrice(final MenuPrice price) {
        this.price = price;
    }

    public void show() {
        priceLessThanMenuProductsPrice(this.price, this.menuProducts);
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public boolean isDisplayed() {
        return this.displayed;
    }

    public MenuPrice price() {
        return this.price;
    }
}
