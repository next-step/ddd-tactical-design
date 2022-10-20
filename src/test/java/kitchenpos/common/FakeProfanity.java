package kitchenpos.common;

import kitchenpos.common.infra.Profanities;

import java.util.List;

public class FakeProfanity implements Profanities {
    private final List<String> values = List.of("damn");

    @Override
    public boolean containsProfanity(final String name) {
        return values.contains(name);
    }
}
