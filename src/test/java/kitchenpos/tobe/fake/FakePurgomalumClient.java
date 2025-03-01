package kitchenpos.tobe.fake;


import kitchenpos.tobe.product.domain.PurgomalumAgent;

import java.util.Arrays;
import java.util.List;

public class FakePurgomalumClient implements PurgomalumAgent {

    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("부적절한이름", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
                .anyMatch(text::contains);
    }
}
