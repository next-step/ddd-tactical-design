package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.InvalidMenuArgumentException;
import kitchenpos.menus.tobe.exception.InvalidMenuPriceException;
import kitchenpos.menus.tobe.vo.MenuName;
import kitchenpos.menus.tobe.vo.MenuPrice;
import kitchenpos.products.tobe.domain.vo.EmptyProfanities;
import kitchenpos.products.tobe.domain.vo.Profanities;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final MenuName name;
    private final MenuPrice price;
    private final MenuGroup menuGroup;
    private final MenuProducts menuProducts;
    private final boolean displayed;

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
        if(price.isGreaterThan(totalAmount)) {
            throw new InvalidMenuPriceException();
        }
    }
}
