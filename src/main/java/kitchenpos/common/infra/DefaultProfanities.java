package kitchenpos.common.infra;

import kitchenpos.common.domain.Profanities;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DefaultProfanities implements Profanities {
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("욕설", "비속어");
    }

    @Override
    public boolean contains(final String text) {
        return profanities.stream()
                .anyMatch(profanity -> text.contains(profanity));
    }
}
