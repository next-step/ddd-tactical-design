package kitchenpos.menus.tobe.domain;

import java.util.UUID;

public class Menu {
    private UUID id;
    private DisplayedName name;
    private Price price;
    private MenuProducts menuProducts;
    private boolean displayed;

    public Menu(UUID id, DisplayedName name, Price price, MenuProducts menuProducts, MenuPricePolicy menuPricePolicy) {
        menuPricePolicy.follow(price, menuProducts.totalPrice());
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuProducts = menuProducts;
        this.displayed = true;
    }

    public void changePrice(Price price, MenuPricePolicy menuPricePolicy) {
        menuPricePolicy.follow(price, menuProducts.totalPrice());
        this.price = price;
    }

    public void display() {
        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
