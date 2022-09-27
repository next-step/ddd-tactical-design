package kitchenpos.global.vo;

import static kitchenpos.global.utils.StringUtils.isBlank;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.global.exception.EmptyNameException;
import kitchenpos.global.exception.ProfanityNameException;
import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
public class Name extends ValueObject {

    @Column(name = "name", nullable = false)
    private String value;

    protected Name() {
    }

    public Name(String value, PurgomalumClient purgomalumClient) {
        validate(value, purgomalumClient);
        this.value = value;
    }

    private void validate(String name, PurgomalumClient purgomalumClient) {
        if (isBlank(name)) {
            throw new EmptyNameException();
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new ProfanityNameException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
