package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.vo.Profanities;

import java.util.List;
import java.util.Objects;


public class FakeProfanitiesClient implements Profanities {

    private final List<String> profanityNames;

    public FakeProfanitiesClient(final List<String> profanityNames) {
        this.profanityNames = profanityNames;
    }

    @Override
    public boolean containsProfanity(final String name) {
        return profanityNames.contains(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FakeProfanitiesClient that = (FakeProfanitiesClient) o;
        return Objects.equals(profanityNames, that.profanityNames);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(profanityNames);
    }
}
