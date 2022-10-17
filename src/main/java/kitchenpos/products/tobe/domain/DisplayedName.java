package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.Profanities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    private static final String EMPTY_NAME_MESSAGE = "상품 이름은 비어있을 수 없습니다.";
    private static final String CONTAIN_PROFANITY_MESSAGE = "상품 이름에 비속어가 포함될 수 없습니다.";

    @Column(name = "name", nullable = false)
    private final String name;

    public DisplayedName(final String name, final Profanities profanities) {
        if (null == name || name.isBlank()) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
        if (profanities.containsProfanity(name)) {
            throw new IllegalArgumentException(CONTAIN_PROFANITY_MESSAGE);
        }
        this.name = name;
    }

    protected DisplayedName() {
        this.name = null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        final DisplayedName that = (DisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
