package kitchenpos.eatinorders.tobe.domain.order.menu;

import java.util.Objects;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Money;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupId;

public final class Menu {

    private final MenuId id;
    private final DisplayedName name;
    private final Money price;
    private final MenuGroupId menuGroupId;
    private final MenuProducts menuProducts;

    public Menu(
        MenuId id,
        DisplayedName name,
        Money price,
        MenuGroupId menuGroupId,
        MenuProducts menuProducts
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
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
        return Objects.equals(id, menu.id)
            && Objects.equals(name, menu.name)
            && Objects.equals(price, menu.price)
            && Objects.equals(menuGroupId, menu.menuGroupId)
            && Objects.equals(menuProducts, menu.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, menuGroupId, menuProducts);
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", name=" + name +
            ", price=" + price +
            ", menuGroupId=" + menuGroupId +
            ", menuProducts=" + menuProducts +
            '}';
    }
}
