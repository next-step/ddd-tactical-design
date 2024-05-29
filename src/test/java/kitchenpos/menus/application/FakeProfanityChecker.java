package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.menu.ProfanityChecker;

import java.util.List;

public class FakeProfanityChecker implements ProfanityChecker {
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
