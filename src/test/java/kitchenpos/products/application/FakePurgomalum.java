package kitchenpos.products.application;

import kitchenpos.common.domain.Purgomalum;

import java.util.Arrays;
import java.util.List;

public class FakePurgomalum implements Purgomalum {

    private static final Purgomalum INSTANCE = new FakePurgomalum();
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
            .anyMatch(profanity -> text.contains(profanity));
    }

    public static Purgomalum create() {
        return INSTANCE;
    }
}
