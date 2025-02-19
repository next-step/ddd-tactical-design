package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuPriceValidationException;
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

    public Menu() {
    }

    public static Menu create(
            final String name,
            final BigDecimal price,
            final boolean isDisplayed,
            final MenuGroup menuGroup,
            final List<MenuProduct> menuProductList,
            final Profanities profanities
    ) {

        MenuProducts menuProducts = MenuProducts.of(menuProductList);

        MenuPrice menuPrice = MenuPrice.of(price, p -> {
            if (price.compareTo(menuProducts.getTotalPrice()) > 0) {
                throw new MenuPriceValidationException("메뉴 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
            }
        });

        Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setName(MenuName.of(name, profanities));
        menu.setPrice(menuPrice);
        menu.setMenuGroup(menuGroup);
        menu.setDisplayed(isDisplayed);
        menu.setMenuProducts(menuProducts);
        return menu;
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
