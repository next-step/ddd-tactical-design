package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private String value;

    protected DisplayedName() {
    }

    private DisplayedName(final String value) {
        this.value = value;
    }

    public static DisplayedName valueOf(final String value, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(value) || purgomalumClient.containsProfanity(value)) {
            throw new InvalidProductNameException();
        }
        return new DisplayedName(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
