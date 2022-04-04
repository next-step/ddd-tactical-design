package kitchenpos;

import kitchenpos.menus.tobe.menu.domain.*;
import kitchenpos.menus.tobe.menu.domain.product.Price;
import kitchenpos.menus.tobe.menu.ui.dto.MenuChangePriceRequest;
import kitchenpos.menus.tobe.menu.ui.dto.MenuCreateRequest;
import kitchenpos.menus.tobe.menu.ui.dto.MenuProductRequest;
import kitchenpos.menus.tobe.menu.ui.dto.ProductResponse;
import kitchenpos.menus.tobe.menugroup.domain.MenuGroup;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class MenuFixture {

    private static final MenuProductLoader loader = new MenuProductLoader();

    public static MenuGroup 메뉴그룹() {
        return 메뉴그룹("메뉴그룹");
    }

    public static MenuGroup 메뉴그룹(final String name) {
        return new MenuGroup(name);
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

    public static List<MenuProduct> 메뉴상품목록1(final UUID... productIds) {
        return Arrays.stream(productIds)
                .map(MenuFixture::메뉴상품)
                .collect(toList());
    }

    public static MenuProducts 금액이불러와진_메뉴상품목록() {
        return 금액이불러와진_메뉴상품목록(ProductFixture.상품(), ProductFixture.상품());
    }

    public static MenuProducts 금액이불러와진_메뉴상품목록(final List<Product> products) {
        final List<MenuProduct> menuProducts = 메뉴상품목록1(products.stream().map(Product::getId).toArray(UUID[]::new));
        final List<ProductResponse> productList = products.stream().map(product -> new ProductResponse(product.getId(), new Price(product.getPrice().value()))).collect(toList());

        return loader.loadMenuProducts(menuProducts, productList);
    }

    public static MenuProducts 금액이불러와진_메뉴상품목록(final Product... products) {
        return 금액이불러와진_메뉴상품목록(Arrays.asList(products));
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

    public static MenuCreateRequest createMenuRequest(
            final String name,
            final long price,
            final UUID menuGroupId,
            final boolean displayed,
            final MenuProductRequest... menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    public static MenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final MenuProductRequest... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    public static MenuCreateRequest createMenuRequest(
            final String name,
            final long price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuProductRequest> menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    public static MenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuProductRequest> menuProducts
    ) {
        return new MenuCreateRequest(name, price, menuGroupId, displayed, menuProducts);
    }

    public static MenuProductRequest createMenuProductRequest(final UUID productId, final long quantity) {
        return new MenuProductRequest(productId, quantity);
    }

    public static MenuChangePriceRequest changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    public static MenuChangePriceRequest changePriceRequest(final BigDecimal price) {
        return new MenuChangePriceRequest(price);
    }

    public static Menu 간단메뉴(Product product) {
        return MenuFixture.메뉴(19_000L, MenuFixture.금액이불러와진_메뉴상품목록(product));
    }

}
