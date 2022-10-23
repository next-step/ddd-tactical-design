package kitchenpos.menu;

import java.util.Random;
import java.util.UUID;
import kitchenpos.common.name.NameFactory;
import kitchenpos.common.price.Price;
import kitchenpos.common.profanity.FakeProfanityDetectService;
import kitchenpos.common.profanity.domain.ProfanityDetectService;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;
import kitchenpos.menu.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.product.tobe.domain.entity.Product;

public class Fixtures {

    private static final ProfanityDetectService profanityDetectService = new FakeProfanityDetectService();

    private static final NameFactory nameFactory = new NameFactory(profanityDetectService);

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), nameFactory.create(name));
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(
            UUID.randomUUID(),
            new Price(10_000),
            new MenuProductQuantity(new Random().nextInt(10))
        );
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(
            UUID.randomUUID(),
            product.price(),
            new MenuProductQuantity(quantity)
        );
    }
}
