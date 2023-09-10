package kitchenpos.profanity.application.port.out;


import java.util.Arrays;
import java.util.List;

public class FakeProfanityFilterPort implements ProfanityFilterPort {
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
