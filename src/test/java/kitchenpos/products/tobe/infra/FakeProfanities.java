package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.Profanities;

import java.util.Arrays;
import java.util.List;

public class FakeProfanities implements Profanities {

    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("욕설", "비속어");
    }

    @Override
    public boolean contains(final String text) {
        return profanities.stream()
                .anyMatch(profanity -> text.contains(profanity));
    }
}
