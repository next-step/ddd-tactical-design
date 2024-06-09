package kitchenpos.products.infra;

import kitchenpos.common.domain.ProfanityValidator;

import java.util.Arrays;
import java.util.List;

public class FakeProfanityValidator implements ProfanityValidator {
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
