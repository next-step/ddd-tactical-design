package kitchenpos.products.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
public class DisplayedName {

    private static final String EMPTY_NAME = "";

    @Column
    private final String name;

    protected DisplayedName() {
        name = EMPTY_NAME;
    }

    public DisplayedName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DisplayedName that = (DisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void validateName(final PurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(name)){
            throw new IllegalArgumentException();
        }
    }
}
