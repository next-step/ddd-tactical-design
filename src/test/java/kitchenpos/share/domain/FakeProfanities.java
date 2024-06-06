package kitchenpos.share.domain;

import kitchenpos.shared.domain.Profanities;

import java.util.List;

public class FakeProfanities implements Profanities {

    private final List<String> profanities;

    public FakeProfanities(String... profanities) {
        this.profanities = List.of(profanities);
    }

    @Override
    public boolean contains(String text) {
        return profanities.contains(text);
    }
}
