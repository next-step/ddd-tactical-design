package kitchenpos.products.tobe.domain;

import java.util.List;
import java.util.Objects;

public class ProfanityName {
    private final List<String> profanityNames;

    public ProfanityName() {
        this(List.of());
    }

    public ProfanityName(final List<String> profanityNames) {
        this.profanityNames = profanityNames;
    }

    public boolean contains(final String name) {
        return profanityNames.contains(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProfanityName that = (ProfanityName) o;
        return Objects.equals(profanityNames, that.profanityNames);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(profanityNames);
    }
}
