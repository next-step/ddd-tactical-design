package kitchenpos.global.domain.vo;

import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import kitchenpos.global.marker.ValueObject;

import java.util.Objects;

public final class DisplayName implements ValueObject {

    private final Name name;

    public DisplayName(String name, BannedWordCheckClient bannedWordCheckClient) {
        this.name = new Name(name);
        validate(this.name, bannedWordCheckClient);
    }

    private void validate(Name name, BannedWordCheckClient bannedWordCheckClient) {
        if (bannedWordCheckClient.containsProfanity(name.getName())) {
            throw new IllegalArgumentException("상품의 이름이 금지어를 포함하고 있습니다.");
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
