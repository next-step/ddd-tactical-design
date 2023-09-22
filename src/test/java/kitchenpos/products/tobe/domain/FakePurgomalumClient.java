package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.PurgomalumClient;

import java.util.Arrays;
import java.util.List;

public class FakePurgomalumClient implements PurgomalumClient {
    private final List<String> profanities = Arrays.asList("fxxk", "slang");

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
                .anyMatch(profanity -> text.contains(profanity));
    }
}
