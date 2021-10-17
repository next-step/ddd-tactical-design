package kitchenpos.common.tobe;

import java.util.Arrays;
import java.util.List;
import kitchenpos.common.infra.Profanities;

public class FakeProfanities implements Profanities {

    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean contains(final String text) {
        return profanities.stream()
            .anyMatch(text::contains);
    }
}
