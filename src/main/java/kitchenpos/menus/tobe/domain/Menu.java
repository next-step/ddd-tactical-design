package kitchenpos.menus.tobe.domain;

import java.util.UUID;
import javax.persistence.Id;

public class Menu {

    @Id
    private final UUID id;

    private MenuPrice price;
    private MenuName name;
    private MenuProducts menuProducts;
    private MenuGroup menuGroup;
    private boolean displayed;

    public Menu(MenuPrice price, MenuName name, MenuProducts menuProducts, MenuGroup menuGroup) {
        validateMenuPrice(price, menuProducts);
        this.id = UUID.randomUUID();
        this.price = price;
        this.name = name;
        this.menuProducts = menuProducts;
        this.menuGroup = menuGroup;
        this.displayed = Boolean.FALSE;
    }

    private void validateMenuPrice(MenuPrice price, MenuProducts menuProducts) {
        if (price.greaterThan(menuProducts.totalPrice())) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(MenuPrice changePrice) {
        validateMenuPrice(changePrice, menuProducts);
        this.price = changePrice;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public void display() {
        this.displayed = Boolean.TRUE;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
