package kitchenpos.common;

import kitchenpos.common.domain.ProfanityPolicy;

import java.util.Arrays;
import java.util.List;

public class FakeProfanityPolicy implements ProfanityPolicy {
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
