package kitchenpos.products.application;

import java.util.Arrays;
import java.util.List;
import kitchenpos.products.infra.PurgomalumClient;

public class FakePurgomalumClient implements PurgomalumClient {

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
