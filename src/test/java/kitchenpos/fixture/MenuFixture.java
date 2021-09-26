package kitchenpos.fixture;

import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.domain.Product;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class MenuFixture {
    public static MenuGroup 메뉴그룹() {
        return 메뉴그룹("메뉴그룹");
    }

    public static MenuGroup 메뉴그룹(final String name) {
        return new MenuGroup(name);
    }

    public static MenuGroup 메뉴그룹(final UUID id, final String name) {
        return new MenuGroup(id, name);
    }

    public static MenuProduct 메뉴상품() {
        return 메뉴상품(UUID.randomUUID(), 2L);
    }

    public static MenuProduct 메뉴상품(final UUID productId) {
        return 메뉴상품(productId, 2L);
    }

    public static MenuProduct 메뉴상품(final UUID productId, final long quantity) {
        return new MenuProduct(productId, new Quantity(quantity));
    }

    public static MenuProducts 메뉴상품목록(final UUID... productIds) {
        return new MenuProducts(
                Arrays.stream(productIds)
                        .map(MenuFixture::메뉴상품)
                        .collect(toList())
        );
    }

    public static MenuProducts 메뉴상품목록() {
        return new MenuProducts(Arrays.asList(메뉴상품(), 메뉴상품()));
    }

    public static MenuProducts 금액이불러와진_메뉴상품목록() {
        return 금액이불러와진_메뉴상품목록(ProductFixture.상품(), ProductFixture.상품());
    }

    public static MenuProducts 금액이불러와진_메뉴상품목록(final Product... products) {
        final MenuProducts menuProducts = 메뉴상품목록(Arrays.stream(products).map(Product::getId).toArray(UUID[]::new));
        Arrays.stream(products)
                .forEach(product -> menuProducts.loadProduct(product));
        return menuProducts;
    }

    public static Menu 메뉴() {
        return 메뉴("메뉴이름", 1000L, 메뉴그룹(), Arrays.asList(메뉴상품(), 메뉴상품()));
    }

    public static Menu 메뉴(final String name, final long price, final MenuGroup menuGroup, final List<MenuProduct> menuProducts) {
        return new Menu(new Name(name, new FakeProfanities()), new Price(price), menuGroup, menuProducts);
    }

    public static Menu 메뉴(final long price, final MenuGroup menuGroup, final MenuProducts menuProducts) {
        return new Menu(new Name("메뉴 이름", new FakeProfanities()), new Price(price), menuGroup, true, menuProducts);
    }

    public static Menu 메뉴(final long price, final MenuProducts menuProducts) {
        return new Menu(new Name("메뉴 이름", new FakeProfanities()), new Price(price), 메뉴그룹(), true, menuProducts);
    }
}
