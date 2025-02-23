package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.InvalidMenuArgumentException;
import kitchenpos.menus.tobe.exception.InvalidMenuPriceException;
import kitchenpos.menus.tobe.vo.MenuName;
import kitchenpos.menus.tobe.vo.MenuPrice;
import kitchenpos.products.tobe.domain.vo.Profanities;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final MenuName name;
    private MenuPrice price;
    private final UUID menuGroupId;
    private MenuProducts menuProducts;
    private boolean displayed;

    public Menu(final UUID id, final String name, final Profanities profanities, final long price, final UUID menuGroupId, final List<MenuProduct> menuProducts, final boolean displayed) {
        this(id, new MenuName(name, profanities), new MenuPrice(price), menuGroupId, new MenuProducts(menuProducts), displayed);
    }

    public Menu(final UUID id, final MenuName name, final MenuPrice price, final UUID menuGroupId, final MenuProducts menuProducts, final boolean displayed) {
        this.verify(name, price, menuGroupId, menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    private void verify(final MenuName name, final MenuPrice price, final UUID menuGroupId, final MenuProducts menuProducts) {
        if (Objects.isNull(name) || Objects.isNull(price) || Objects.isNull(menuGroupId) || Objects.isNull(menuProducts)) {
            throw new InvalidMenuArgumentException();
        }
        long totalAmount = menuProducts.totalAmount();
        if (price.isGreaterThan(totalAmount)) {
            throw new InvalidMenuPriceException();
        }
    }

    public void changePrice(final long changedPrice) {
        final MenuPrice changedMenuPrice = new MenuPrice(changedPrice);
        final long totalAmount = menuProducts.totalAmount();
        if (changedMenuPrice.isGreaterThan(totalAmount)) {
            throw new InvalidMenuPriceException();
        }
        this.price = changedMenuPrice;
    }

    public void changedProductPrice(final long productId, final long changedFirstPrice) {
        menuProducts.changedProductPrice(productId, changedFirstPrice);
        final long totalAmount = menuProducts.totalAmount();
        this.displayed = !(price.isGreaterThan(totalAmount));
    }

    public void display() {
        long totalAmount = menuProducts.totalAmount();
        if(price.isGreaterThan(totalAmount)) {
            throw new IllegalStateException();
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID getId() {
        return id;
    }

    public boolean hasProduct(final Long productId) {
        return menuProducts.hasProduct(productId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
