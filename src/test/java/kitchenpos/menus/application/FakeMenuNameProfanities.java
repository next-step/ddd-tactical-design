package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.menu.MenuNameProfanities;

import java.util.Arrays;
import java.util.List;

public class FakeMenuNameProfanities implements MenuNameProfanities {

    private final List<String> profanities;

    public FakeMenuNameProfanities() {
        this.profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
                .anyMatch(text::contains);
    }
}
