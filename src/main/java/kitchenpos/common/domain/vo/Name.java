package kitchenpos.common.domain.vo;

import kitchenpos.common.domain.infra.PurgomalumValidator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
    @Column()
    private String name;

    public Name(String name, PurgomalumValidator purgomalumValidator) {
        if (Objects.isNull(name) || purgomalumValidator.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected Name() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name that = (Name) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
