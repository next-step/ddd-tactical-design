package kitchenpos.products.application;

import kitchenpos.products.infra.ProductProfanity;

import java.util.Arrays;
import java.util.List;

public class FakeProductProfanityChecker implements ProductProfanity {
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
            .anyMatch(profanity -> text.contains(profanity));
    }

    @Override
    public void profanityCheck(String text) {
        if (containsProfanity(text)) {
            throw new IllegalArgumentException();
        }
    }
}
