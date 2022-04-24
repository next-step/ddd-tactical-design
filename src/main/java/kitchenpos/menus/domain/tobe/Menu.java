package kitchenpos.menus.domain.tobe;


import kitchenpos.products.domain.tobe.BanWordFilter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Menu {
    private static final String MENU_GROUP_NULL_NOT_ALLOWED = "menuGroup이 있어야합니다.";
    private static final String INVALID_MENU_PRICE = "잘못된 메뉴 가격입니다.";

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    private UUID menuGroupId;

    @Embedded
    private MenuDisplayed displayed;

    @Embedded
    private MenuProducts menuProducts;

    public Menu(String name, BanWordFilter banWordFilter, MenuPrice price, UUID menuGroupId, boolean displayed, List<MenuProduct> menuProducts) {
        this(UUID.randomUUID(), name, banWordFilter, price, menuGroupId, displayed, new MenuProducts(menuProducts));
    }

    public Menu(UUID id, String name, BanWordFilter banWordFilter, MenuPrice price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        validate(menuGroupId);
        this.id = id;
        this.name = new MenuName(name, banWordFilter);
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = new MenuDisplayed(displayed);
        this.menuProducts = menuProducts;
    }

    private void validate(UUID menuGroupId) {
        if (menuGroupId == null) {
            throw new IllegalArgumentException(MENU_GROUP_NULL_NOT_ALLOWED);
        }
    }

    public void show() {
        this.displayed.show();
    }

    public void hide() {
        this.displayed.hide();
    }

    public void changePrice(MenuPrice price) {
        this.price = price;
    }

    public boolean getDisplayed() {
        return displayed.isDisplayed();
    }

    public MenuPrice getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public UUID getId() {
        return id;
    }

    private void validateMenuPrice(MenuPrice menuPrice, MenuPrice menuProductsTotalPrice) {
        if (menuPrice.isBiggerThan(menuProductsTotalPrice)) {
            throw new IllegalArgumentException(INVALID_MENU_PRICE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
