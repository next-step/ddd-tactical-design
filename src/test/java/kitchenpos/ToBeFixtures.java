package kitchenpos;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.ProductId;
import kitchenpos.menus.domain.tobe.domain.menu.Displayed;
import kitchenpos.menus.domain.tobe.domain.menu.Menu;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProductPrice;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProductQuantity;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProductSeq;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.domain.menugroup.Name;
import kitchenpos.products.domain.tobe.domain.DisplayedName;
import kitchenpos.products.domain.tobe.domain.Price;
import kitchenpos.products.domain.tobe.domain.Product;

public class ToBeFixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    //

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return menuGroup(UUID.randomUUID(), name);
    }

    public static MenuGroup menuGroup(final UUID id, final String name) {
        return new MenuGroup(
            new MenuGroupId(id),
            new Name(name)
        );
    }

    //

    public static Menu menu() {
        return new Menu(
            new MenuId(UUID.randomUUID()),
            new kitchenpos.menus.domain.tobe.domain.menu.DisplayedName(
                "후라이드+간장치킨",
                new FakeProfanities()
            ),
            new kitchenpos.menus.domain.tobe.domain.menu.Price(BigDecimal.valueOf(33_000L)),
            new MenuGroupId(UUID.randomUUID()),
            new Displayed(true),
            menuProducts()
        );
    }

    //

    public static MenuProducts menuProducts() {
        final MenuProduct menuProduct1 = menuProduct(
            product("후라이드", 16_000L),
            1L
        );
        final MenuProduct menuProduct2 = menuProduct(
            product("간장치킨", 17_000L),
            1L
        );
        return new MenuProducts(Arrays.asList(menuProduct1, menuProduct2));
    }

    public static MenuProduct menuProduct() {
        final Product product = product();
        return menuProduct(product.getId(), product.getPrice().getValue(), 2L);
    }

    public static MenuProduct menuProduct(
        final Product product,
        final Long quantity
    ) {
        return menuProduct(
            new Random().nextLong(),
            product.getId(),
            product.getPrice().getValue(),
            quantity
        );
    }

    public static MenuProduct menuProduct(
        final ProductId productId,
        final BigDecimal price,
        final Long quantity
    ) {
        return menuProduct(new Random().nextLong(), productId, price, quantity);
    }

    public static MenuProduct menuProduct(
        final Long seq,
        final ProductId productId,
        final BigDecimal price,
        final Long quantity
    ) {
        return new MenuProduct(
            new MenuProductSeq(seq),
            productId,
            new MenuProductPrice(price),
            new MenuProductQuantity(quantity)
        );
    }

    //

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String displayedName, final long price) {
        return product(new ProductId(UUID.randomUUID()), displayedName, price);
    }

    public static Product product(ProductId id, final String displayedName, final long price) {
        return new Product(
            id,
            new DisplayedName(displayedName, new FakeProfanities()),
            new Price(BigDecimal.valueOf(price))
        );
    }
}
