package kitchenpos.products.tobe.fakes;

import kitchenpos.products.tobe.domain.ProfanitiesChecker;

import java.util.Arrays;
import java.util.List;

public class FakePurgomalums implements ProfanitiesChecker {
    private final List<String> profanities;

    public FakePurgomalums(String... profanities) {
        this.profanities = Arrays.stream(profanities).toList();
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
                .anyMatch(profanity -> text.contains(profanity));
    }
}
