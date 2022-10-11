package kitchenpos.products.application;

import kitchenpos.products.infra.PurgomalumClient;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class FakePurgomalumClient implements PurgomalumClient {
    private static final List<String> profanities;

    static {
        profanities = Arrays.asList("비속어", "욕설");
    }

    @Override
    public boolean containsProfanity(final String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }

        return profanities.stream()
            .anyMatch(profanity -> text.contains(profanity));
    }
}
