package kitchenpos.menus.tobe.domain.menu;

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
