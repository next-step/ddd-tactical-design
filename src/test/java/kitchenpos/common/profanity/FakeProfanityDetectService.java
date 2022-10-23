package kitchenpos.common.profanity;

import java.util.Arrays;
import java.util.List;
import kitchenpos.common.profanity.domain.ProfanityDetectService;

public class FakeProfanityDetectService implements ProfanityDetectService {

    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean profanityIn(final String text) {
        return profanities.stream()
            .anyMatch(text::contains);
    }
}
