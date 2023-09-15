package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.MenuPurgomalumClient;

import java.util.Arrays;
import java.util.List;

public class FakeMenuPurgomalumClient implements MenuPurgomalumClient {
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
            .anyMatch(profanity -> text.contains(profanity));
    }
}
