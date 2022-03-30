package kitchenpos.fake.global.infrastructure.external;

import kitchenpos.global.infrastructure.external.BannedWordCheckClient;

import java.util.Collections;
import java.util.List;

public class FakeBannedWordCheckClient implements BannedWordCheckClient {

    private static final List<String> PROFANITIES = Collections.singletonList("존X");

    @Override
    public boolean containsProfanity(String text) {
        return PROFANITIES.stream()
                .anyMatch(text::contains);
    }
}
