package kitchenpos.products.application;

import kitchenpos.core.shared.domain.ProfanityChecker;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class FakeProfanityChecker implements ProfanityChecker {
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        if(StringUtils.isEmpty(text)) {
            return false;
        }
        return profanities.stream()
            .anyMatch(profanity -> text.contains(profanity));
    }
}
