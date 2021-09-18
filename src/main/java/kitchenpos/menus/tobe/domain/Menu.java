package kitchenpos.menus.tobe.domain;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.domain.exception.WrongPriceException;

import java.util.UUID;

import static kitchenpos.menus.tobe.domain.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE;

public class Menu {
    private final UUID id;

    private final DisplayedName name;

    private Price price;

    private boolean displayed;

    private final MenuGroup menuGroup;

    private final MenuProducts menuProducts;

    public Menu(final UUID id, final DisplayedName name, final Price price, final boolean displayed,
                final MenuGroup menuGroup, final MenuProducts menuProducts, final MenuCreateValidator validator) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
        validator.validate(this);
    }

    public void changePrice(Price price) {
        validatePrice(price, this.getMenuProducts());
        this.price = new Price(price.getValue());
    }

    private void validatePrice(final Price price, final MenuProducts menuProducts) {
        if (price.compareTo(menuProducts.getTotalMenuProductsPrice()) > 0) {
            throw new WrongPriceException(PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE);
        }
    }

    public void display() {
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
