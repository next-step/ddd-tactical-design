package kitchenpos.menus.tobe.domain;




import kitchenpos.menus.tobe.domain.vo.ProfanityClient;

import java.util.Arrays;
import java.util.List;

public class FakeProfanitiesClient implements ProfanityClient {
    private final List<String> profanities;

    public FakeProfanitiesClient(final String ...profanities) {
        this(Arrays.stream(profanities).toList());
    }

    public FakeProfanitiesClient(final List<String> profanities) {
        this.profanities = profanities;
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream()
                .anyMatch(profanity -> text.contains(profanity));
    }
}
