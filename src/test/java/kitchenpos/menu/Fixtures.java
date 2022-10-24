package kitchenpos.menu;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.common.name.NameFactory;
import kitchenpos.common.price.Price;
import kitchenpos.common.profanity.FakeProfanityDetectService;
import kitchenpos.common.profanity.domain.ProfanityDetectService;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;
import kitchenpos.menu.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.product.tobe.domain.entity.Product;

public class Fixtures {

    private static final ProfanityDetectService profanityDetectService = new FakeProfanityDetectService();

    private static final NameFactory nameFactory = new NameFactory(profanityDetectService);

    public static Menu menu(
        final long price,
        final boolean displayed,
        final MenuProduct... menuProducts
    ) {
        return new Menu(
            UUID.randomUUID(),
            nameFactory.create("후라이드+후라이드"),
            displayed,
            new Price(price),
            menuGroup(),
            Arrays.asList(menuProducts)
        );
    }

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), nameFactory.create(name));
    }

    public static MenuProduct menuProduct(final Price price, final MenuProductQuantity quantity) {
        return new MenuProduct(
            UUID.randomUUID(),
            price,
            quantity
        );
    }

    public static MenuProduct menuProduct(final long price, final long quantity) {
        return menuProduct(
            new Price(price),
            new MenuProductQuantity(quantity)
        );
    }

    public static MenuProduct menuProduct() {
        return menuProduct(
            new Price(10_000),
            new MenuProductQuantity(new Random().nextInt(10))
        );
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return menuProduct(
            product.price(),
            new MenuProductQuantity(quantity)
        );
    }
}
