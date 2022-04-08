package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Menu {
    private static final String INVALID_PRICE_MESSAGE = "잘못된 메뉴 가격 입니다.";
    private static final String INVALID_MENU_GROUP_MESSAGE = "메뉴 그룹이 지정되지 않았습니다.";

    private final UUID id;
    private final DisplayedName displayedName;
    private MenuPrice price;
    private final MenuProducts menuProducts;
    private final MenuGroup menuGroup;
    private boolean displayed;

    public Menu(String displayedName, BigDecimal price, MenuGroup menuGroup, boolean displayed, PurgomalumClient purgomalumClient, MenuProduct... menuProducts) {
        validateMenuGroup(menuGroup);
        validPrice(price, new MenuProducts(menuProducts));
        this.id = UUID.randomUUID();
        this.displayedName = new DisplayedName(displayedName, purgomalumClient);
        this.price = new MenuPrice(price);
        this.menuProducts = new MenuProducts(menuProducts);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
    }

    private void validateMenuGroup(MenuGroup menuGroup) {
        if (Objects.isNull(menuGroup)) {
            throw new IllegalArgumentException(INVALID_MENU_GROUP_MESSAGE);
        }
    }

    private void validPrice(BigDecimal price, MenuProducts menuProducts) {
        if (price.compareTo(menuProducts.totalPrice()) > 0) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
    }

    public void displayCheck() {
        if (price.compareTo(menuProducts.totalPrice()) > 0) {
            this.displayed = false;
        }
    }

    public void changePrice(BigDecimal price) {
        this.price = new MenuPrice(price);
    }

    public void display() {
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
