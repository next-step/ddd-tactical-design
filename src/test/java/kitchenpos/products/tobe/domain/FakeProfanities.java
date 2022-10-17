package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.Profanities;

import java.util.List;

class FakeProfanities implements Profanities {
    private final List<String> values = List.of("damn");

    @Override
    public boolean containsProfanity(final String text) {
        return values.contains(text);
    }
}
