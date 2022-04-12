package kitchenpos.products.application;

import kitchenpos.support.infra.Profanity;

import java.util.Arrays;
import java.util.List;

public class FakePurgomalumClient implements Profanity {
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
