package kitchenpos.products.application;

import kitchenpos.products.domain.ProductPurgomalumClient;

import java.util.Arrays;
import java.util.List;

public class FakeProductPurgomalumClient implements ProductPurgomalumClient {
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
            .anyMatch(text::contains);
    }
}
