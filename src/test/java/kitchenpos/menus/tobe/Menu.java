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
    private final MenuGroup menuGroup;
    private MenuProducts menuProducts;
    private boolean displayed;

    public Menu(final UUID id, final String name, final Profanities profanities, final long price, final MenuGroup menuGroup, final List<MenuProduct> menuProducts, final boolean displayed) {
        this(id, new MenuName(name, profanities), new MenuPrice(price), menuGroup, new MenuProducts(menuProducts), displayed);
    }

    public Menu(final UUID id, final MenuName name, final MenuPrice price, final MenuGroup menuGroup, final MenuProducts menuProducts, final boolean displayed) {
        this.verify(name, price, menuGroup, menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    private void verify(final MenuName name, final MenuPrice price, final MenuGroup menuGroup, final MenuProducts menuProducts) {
        if (Objects.isNull(name) || Objects.isNull(price) || Objects.isNull(menuGroup) || Objects.isNull(menuProducts)) {
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
        final MenuProducts changedMenuProducts = menuProducts.changedProductPrice(productId, changedFirstPrice);
        final long totalAmount = changedMenuProducts.totalAmount();
        this.menuProducts = changedMenuProducts;
        this.displayed = !(price.isGreaterThan(totalAmount));
    }

    public void display() {
        long totalAmount = menuProducts.totalAmount();
        if(price.isGreaterThan(totalAmount)) {
            throw new IllegalStateException();
        }
    }
}
