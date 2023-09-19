package kitchenpos.application;

import java.util.Arrays;
import java.util.List;
import kitchenpos.common.profanity.ProfanityClient;

public class FakeProfanityClient implements ProfanityClient {

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
