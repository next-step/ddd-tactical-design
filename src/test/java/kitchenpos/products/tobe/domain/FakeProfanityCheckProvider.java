package kitchenpos.products.tobe.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;

public class FakeProfanityCheckProvider implements ProfanityCheckProvider {

    private final Set<String> profanities;

    public FakeProfanityCheckProvider(@NonNull final String... profanity) {
        this.profanities = Arrays.stream(profanity).collect(Collectors.toSet());
    }

    @Override
    public boolean containsProfanity(final String value) {
        return profanities.stream().anyMatch(profanity -> profanity.equals(value));
    }
}
