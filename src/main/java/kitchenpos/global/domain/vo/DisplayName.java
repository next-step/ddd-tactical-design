package kitchenpos.global.domain.vo;

import kitchenpos.global.marker.ValueObject;
import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import kitchenpos.products.tobe.exception.IllegalProductNameException;

import java.util.Objects;

public final class DisplayName implements ValueObject {

    private final String name;

    public DisplayName(String name, BannedWordCheckClient bannedWordCheckClient) {
        validate(name, bannedWordCheckClient);
        this.name = name;
    }

    private void validate(String name, BannedWordCheckClient bannedWordCheckClient) {
        if (Objects.isNull(name) || bannedWordCheckClient.containsProfanity(name)) {
            throw new IllegalProductNameException();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayName that = (DisplayName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
