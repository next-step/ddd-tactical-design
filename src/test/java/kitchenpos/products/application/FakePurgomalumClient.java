package kitchenpos.products.application;

import kitchenpos.common.external.infra.ProfanityChecker;

import java.util.Arrays;
import java.util.List;

public class FakePurgomalumClient implements ProfanityChecker {
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
