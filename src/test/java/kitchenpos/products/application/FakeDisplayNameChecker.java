package kitchenpos.products.application;

import kitchenpos.common.domain.DisplayNameChecker;

import java.util.Arrays;
import java.util.List;

public class FakeDisplayNameChecker implements DisplayNameChecker {
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
            .anyMatch(profanity -> text.contains(profanity));
    }
}
