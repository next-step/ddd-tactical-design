package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.vo.Profanities;

import java.util.List;
import java.util.Objects;

public class FakeProfanities implements Profanities {
    private final List<String> profanityNames;

    public FakeProfanities() {
        this(List.of());
    }

    public FakeProfanities(final List<String> profanityNames) {
        this.profanityNames = profanityNames;
    }

    @Override
    public boolean contains(final String name) {
        return profanityNames.contains(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FakeProfanities that = (FakeProfanities) o;
        return Objects.equals(profanityNames, that.profanityNames);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(profanityNames);
    }
}
