package kitchenpos.apply.menu.tobe.infra;


import kitchenpos.apply.menus.tobe.domain.PurgomalumClient;

import java.util.Arrays;
import java.util.List;

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
