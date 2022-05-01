package kitchenpos.menus.tobe.domain;

import java.util.Arrays;
import java.util.List;

import kitchenpos.menus.tobe.domain.vo.Profanities;

public class FakeProfanities implements Profanities {

    private final List<String> values = Arrays.asList("비속어", "욕설");

    @Override
    public boolean contains(final String text) {
        return values.stream()
                     .anyMatch(value -> text.contains(value));
    }
}
