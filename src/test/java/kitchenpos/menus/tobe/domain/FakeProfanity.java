package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Profanity;

import java.util.List;

class FakeProfanity implements Profanity {
    private final List<String> values = List.of("damn");

    @Override
    public boolean isContains(final String name) {
        return values.contains(name);
    }
}
