package kitchenpos.products.tobe.domain;



import kitchenpos.products.tobe.domain.vo.Profanities;

import java.util.Arrays;
import java.util.List;

public class FakeProfanitiesClient implements Profanities {
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
