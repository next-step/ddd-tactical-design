package kitchenpos.menus.tobe.domain;

public class Menu {
    private static final String PRICE_INVALID_MESSAGE = "메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.";

    private Price price;
    private DisplayedName name;
    private MenuProduct menuProduct;
    private MenuGroup menuGroup;
    private boolean displayed;

    public Menu(final Price price, final DisplayedName name, final MenuProduct menuProduct, final MenuGroup menuGroup) {
        if (menuProduct.getSumOfPrice().compareTo(price) < 0) {
            throw new IllegalArgumentException(PRICE_INVALID_MESSAGE);
        }
        this.price = price;
        this.name = name;
        this.menuProduct = menuProduct;
        this.menuGroup = menuGroup;
        this.displayed = true;
    }

    public Price getPrice() {
        return price;
    }

    public void changePrice(final Price price) {
        this.price = price;
    }

    public boolean isDisplayed() {
        return this.displayed;
    }
    public void displayOff() {
        this.displayed = false;
    }

    public void displayOn() {
        this.displayed = true;
    }
}
