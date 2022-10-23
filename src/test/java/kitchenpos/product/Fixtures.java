package kitchenpos.product;

import java.util.UUID;
import kitchenpos.common.name.NameFactory;
import kitchenpos.common.price.Price;
import kitchenpos.common.profanity.FakeProfanityDetectService;
import kitchenpos.common.profanity.domain.ProfanityDetectService;
import kitchenpos.product.tobe.domain.entity.Product;

public class Fixtures {

    private static final ProfanityDetectService profanityDetectService = new FakeProfanityDetectService();

    private static final NameFactory nameFactory = new NameFactory(profanityDetectService);

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(
            UUID.randomUUID(),
            nameFactory.create(name),
            new Price(price)
        );
    }
}
