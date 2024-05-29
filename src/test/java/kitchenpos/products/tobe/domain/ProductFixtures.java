package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

/**
 * @noinspection UnusedReturnValue
 */
public class ProductFixtures {

    private static final String VALID_NAME = "상품명";
    private static final int VALID_PRICE = 10_000;

    private ProductFixtures() {
        throw new UnsupportedOperationException();
    }

    public static Product create() {
        final var profanityCheckProvider = new FakeProfanityCheckProvider("욕설", "비속어");
        return create(VALID_NAME, VALID_PRICE, profanityCheckProvider);
    }

    public static Product create(final String name, final int price,
        final ProfanityCheckProvider profanityCheckProvider) {
        return Product.create(name, BigDecimal.valueOf(price), profanityCheckProvider);
    }

    public static Product withPrice(final int price) {
        return create(VALID_NAME, price, (value) -> false);
    }

    public static Product withName(final String name,
        final ProfanityCheckProvider profanityCheckProvider) {
        return create(name, VALID_PRICE, profanityCheckProvider);
    }
}
