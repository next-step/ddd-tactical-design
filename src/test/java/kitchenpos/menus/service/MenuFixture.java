package kitchenpos.menus.service;

import java.util.List;
import java.util.UUID;

import kitchenpos.menus.domain.DisplayedName;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuPricePolicy;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.Price;
import kitchenpos.menus.tobe.domain.FakePurgomalumClient;
import kitchenpos.products.domain.Product;

public class MenuFixture {
    private UUID id;
    private String name;
    private long price;
    private boolean displayed;
    private UUID menuGroupId;
    private String menuGroupName;
    private List<MenuProduct> menuProducts;

    public MenuFixture() {
        id = UUID.randomUUID();
        name = "내일의 치킨";
        price = 15000L;
        menuGroupId = null;
    }

    public static MenuFixture builder() {
        return new MenuFixture();
    }

    public static MenuFixture builder(MenuGroup menuGroup) {
        return builder()
                .menuGroup(menuGroup)
                .price(1000L)
                .displayed(true);
    }

    public static MenuFixture builder(MenuGroup menuGroup, Product product) {
        return builder(menuGroup)
                .menuProducts(List.of(MenuProductFixture.builder(product).build()));
    }

    public MenuFixture name(String name) {
        this.name = name;
        return this;
    }

    public MenuFixture price(long price) {
        this.price = price;
        return this;
    }

    public MenuFixture menuGroup(MenuGroup menuGroup) {
        menuGroupId = null;
        menuGroupName = null;
        if (menuGroup != null) {
            menuGroupId = menuGroup.getId();
            menuGroupName = menuGroup.getName();
        }
        return this;
    }

    public MenuFixture menuProduct(MenuProduct menuProduct) {
        menuProducts = List.of(menuProduct);
        return this;
    }

    public MenuFixture menuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
        return this;
    }

    public MenuFixture displayed(boolean displayed) {
        this.displayed = displayed;
        return this;
    }

    public Menu build() {
        return new Menu(
                id,
                new DisplayedName(name, new FakePurgomalumClient()),
                new Price(price),
                new MenuGroup(menuGroupId, menuGroupName),
                new MenuProducts(menuProducts), new MenuPricePolicy()
        );
    }

}
