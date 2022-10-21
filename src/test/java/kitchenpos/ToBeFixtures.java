package kitchenpos;


import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.products.tobe.domain.Name;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public class ToBeFixtures {

    public static Menu menu(final String menuName, final long price, final boolean displayed, final MenuProduct... menuProduct) {
        MenuProducts menuProducts = new MenuProducts();
        Arrays.stream(menuProduct).forEach(menuProducts::add);
        return new Menu(new Name(menuName, false), new Price(BigDecimal.valueOf(price)), menuProducts, new MenuGroup(UUID.randomUUID(), new MenuGroupName("메뉴그룹")));
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(product, new Quantity(BigDecimal.valueOf(quantity)));
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), new MenuGroupName(name));
    }

    public static Product product(final String name, final long price) {
        return new Product(UUID.randomUUID(), new Name(name, false), new Price(BigDecimal.valueOf(price)));
    }

}
