package kitchenpos.menus.tobe.domain;

public class Menu {
    private MenuPrice price;
    private DisplayedName name;

    public Menu(final MenuPrice price, final DisplayedName name) {
        this.price = price;
        this.name = name;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public void changePrice(final MenuPrice price) {
        this.price = price;
    }
}
