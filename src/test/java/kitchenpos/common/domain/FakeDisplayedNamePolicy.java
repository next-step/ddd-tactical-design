package kitchenpos.common.domain;

import java.util.Arrays;
import java.util.List;

public class FakeDisplayedNamePolicy implements DisplayedNamePolicy {

    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean hasProfanity(String name) {
        return profanities.stream()
            .anyMatch(name::contains);
    }
}
