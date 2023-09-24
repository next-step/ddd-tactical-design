package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.ProductNameProfanities;

import java.util.Arrays;
import java.util.List;

public class FakeProductNameProfanities implements ProductNameProfanities {
    private final List<String> profanities;

    public FakeProductNameProfanities() {
        this.profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
                .anyMatch(profanity -> text.contains(profanity));
    }
}
