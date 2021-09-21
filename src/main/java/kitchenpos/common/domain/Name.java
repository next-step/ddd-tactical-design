package kitchenpos.common.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    @Column(name = "name", nullable = false)
    private String value;

    protected Name() {}

    public Name(final String value, final PurgomalumClient profanities) {
        if (Objects.isNull(value) || profanities.containsProfanity(value)) {
            throw new IllegalArgumentException("이름에는 욕설이 포함될 수 없습니다.");
        }
        this.value = value;
    }

    public String getName() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
