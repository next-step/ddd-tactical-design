package kitchenpos.products.application;

import kitchenpos.common.infra.PurgomalumClient;

import java.util.List;

public class FakePurgomalumClient implements PurgomalumClient {
    private static final List<String> profanities;

    static {
        profanities = List.of("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
            .anyMatch(text::contains);
    }
}
