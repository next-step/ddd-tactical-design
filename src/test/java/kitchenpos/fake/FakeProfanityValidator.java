package kitchenpos.fake;

import java.util.List;
import kitchenpos.products.domain.ProfanityValidator;

public class FakeProfanityValidator implements ProfanityValidator {

    private static final List<String> profanities = List.of("비속어", "욕설");

    @Override
    public boolean containsProfanity(String text) {
        return profanities.stream()
                .anyMatch(text::contains);
    }
}
