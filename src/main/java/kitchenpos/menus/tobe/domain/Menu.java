package kitchenpos.menus.tobe.domain;

public class Menu {
    private static final String CAN_NOT_DISPLAY_MESSAGE = "메뉴의 가격은 상품 가격의 합보다 작다면 전시할 수 없습니다.";
    private Price price;
    private DisplayedName name;
    private MenuProduct menuProduct;
    private MenuGroup menuGroup;
    private boolean displayed;

    public Menu(final Price price, final DisplayedName name, final MenuProduct menuProduct, final MenuGroup menuGroup) {
        decideDisplay(menuProduct, price);
        this.price = price;
        this.name = name;
        this.menuProduct = menuProduct;
        this.menuGroup = menuGroup;
    }

    public Price getPrice() {
        return price;
    }

    public void changePrice(final Price price) {
        decideDisplay(this.menuProduct, price);
        this.price = price;
    }

    public boolean isDisplayed() {
        return this.displayed;
    }

    public void displayOff() {
        this.displayed = false;
    }

    public void displayOn() {
        validateDisplay();
        this.displayed = true;
    }

    private void decideDisplay(final MenuProduct menuProduct, final Price price) {
        if (menuProduct.getSumOfPrice().compareTo(price) < 0) {
            this.displayed = false;
            return;
        }
        this.displayed = true;
    }

    private void validateDisplay() {
        if (menuProduct.getSumOfPrice().compareTo(price) < 0) {
            throw new IllegalArgumentException(CAN_NOT_DISPLAY_MESSAGE);
        }
    }
}
