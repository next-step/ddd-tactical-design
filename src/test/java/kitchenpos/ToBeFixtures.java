package kitchenpos;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.ProductId;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProductQuantity;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProductSeq;
import kitchenpos.menus.domain.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.domain.menugroup.Name;
import kitchenpos.products.domain.tobe.domain.DisplayedName;
import kitchenpos.products.domain.tobe.domain.FakeProfanities;
import kitchenpos.products.domain.tobe.domain.Price;
import kitchenpos.products.domain.tobe.domain.Product;

public class ToBeFixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

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

    public static MenuProduct menuProduct() {
        final Product product = product();
        return menuProduct(product.getId(), 2L);
    }

    public static MenuProduct menuProduct(final ProductId productId, final Long quantity) {
        return menuProduct(new Random().nextLong(), productId, quantity);
    }

    public static MenuProduct menuProduct(
        final Long seq,
        final ProductId productId,
        final Long quantity
    ) {
        return new MenuProduct(
            new MenuProductSeq(seq),
            productId,
            new MenuProductQuantity(quantity)
        );
    }

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
