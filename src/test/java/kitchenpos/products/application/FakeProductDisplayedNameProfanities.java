package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.ProductDisplayedNameProfanities;

import java.util.Arrays;
import java.util.List;

public class FakeProductDisplayedNameProfanities implements ProductDisplayedNameProfanities {
    private final List<String> profanities;

    public FakeProductDisplayedNameProfanities() {
        this.profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
            .anyMatch(profanity -> text.contains(profanity));
    }
}
