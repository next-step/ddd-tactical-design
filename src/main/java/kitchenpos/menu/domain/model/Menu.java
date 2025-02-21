package kitchenpos.menu.domain.model;

import kitchenpos.shared.domain.Profanities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Menu {
    private UUID id;
    private MenuName name;
    private MenuPrice price;
    private MenuGroup menuGroup;
    private boolean displayed;
    private MenuProducts menuProducts;
    private UUID menuGroupId;

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

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.value();
    }

    public void setName(final MenuName name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public void setPrice(final MenuPrice price) {
        this.price = price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.value();
    }

    public void setMenuProducts(final MenuProducts menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }
}
