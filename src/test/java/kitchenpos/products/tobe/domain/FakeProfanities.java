package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.Menu.Profanities;

import java.util.Arrays;
import java.util.List;

class FakeProfanities implements Profanities {
    private final List<String> values = Arrays.asList("욕설", "비속어");

    @Override
    public boolean contains(final String text) {
        return values.stream()
                .anyMatch(text::contains);
    }
}


