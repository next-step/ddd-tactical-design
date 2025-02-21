package kitchenpos.menu.domain.model;

import kitchenpos.shared.domain.Profanities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final MenuName name;
    private MenuPrice price;
    private final MenuGroup menuGroup;
    private boolean displayed;
    private final MenuProducts menuProducts;
    private final UUID menuGroupId;

    private Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts, UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public static Menu create(
            final String name,
            final BigDecimal price,
            final boolean isDisplayed,
            final MenuGroup menuGroup,
            final List<MenuProduct> menuProductList,
            final Profanities profanities
    ) {
        return create(UUID.randomUUID(), name, price, isDisplayed, menuGroup, menuProductList, profanities);
    }

    public static Menu create(
            final UUID id,
            final String name,
            final BigDecimal price,
            final boolean isDisplayed,
            final MenuGroup menuGroup,
            final List<MenuProduct> menuProductList,
            final Profanities profanities
    ) {

        MenuName menuName = MenuName.of(name, profanities);
        MenuProducts menuProducts = MenuProducts.of(menuProductList);
        MenuPrice menuPrice = MenuPrice.of(price, menuProducts.getTotalPrice());
        return new Menu(id, menuName, menuPrice, menuGroup, isDisplayed, menuProducts, menuGroup.getId());
    }

    public void changeMenuProductPrice(UUID productId, BigDecimal price) {
        menuProducts.changeMenuProductPrice(productId, price);
    }

    public void hide() {
        this.displayed = false;
    }

    public void display() {
        this.price.validatePrice(menuProducts.getTotalPrice());
        this.displayed = true;
    }

    public void changePrice(BigDecimal price) {
        this.price = MenuPrice.of(price, menuProducts.getTotalPrice());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.value();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
