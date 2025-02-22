package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuValidationException;
import kitchenpos.shared.domain.Profanities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final MenuName name;
    private MenuPrice price;
    private boolean displayed;
    private final MenuProducts menuProducts;
    private final UUID menuGroupId;

    public Menu(UUID id, MenuName name, MenuPrice price, boolean displayed, MenuProducts menuProducts, UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public static Menu create(
            final String name,
            final BigDecimal price,
            final boolean isDisplayed,
            final UUID menuGroupId,
            final List<MenuProduct> menuProductList,
            final Profanities profanities
    ) {
        return create(UUID.randomUUID(), name, price, isDisplayed, menuGroupId, menuProductList, profanities);
    }

    public static Menu create(
            final UUID id,
            final String name,
            final BigDecimal price,
            final boolean isDisplayed,
            final UUID menuGroupId,
            final List<MenuProduct> menuProductList,
            final Profanities profanities
    ) {
        if (menuGroupId == null) {
            throw new MenuValidationException("메뉴 그룹을 반드시 선택해야 합니다.");
        }
        MenuName menuName = MenuName.of(name, profanities);
        MenuProducts menuProducts = MenuProducts.of(menuProductList);
        MenuPrice menuPrice = MenuPrice.of(price, menuProducts.getTotalPrice());
        return new Menu(id, menuName, menuPrice, isDisplayed, menuProducts, menuGroupId);
    }

    public void changeMenuProductPrice(UUID productId, BigDecimal price) {
        menuProducts.changeMenuProductPrice(productId, price);
    }

    public void hide() {
        this.displayed = false;
    }

    public void display() {
        this.price.validateMenuPriceAgainstTotalProductPrice(menuProducts.getTotalPrice());
        this.displayed = true;
    }

    public void changePrice(BigDecimal price) {
        this.price = MenuPrice.of(price, menuProducts.getTotalPrice());
    }

    public void hideMenuWhenMenuProductTotalPriceLowerThanMenuPrice() {
        if (this.price.isGreaterThan(menuProducts.getTotalPrice())) {
            this.displayed = false;
        }
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
