package kitchenpos.products.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import kitchenpos.products.domain.PurgomalumClient;

@Embeddable
public class Name {

    @Column(name = "name", nullable = false)
    private String name;

    public Name() {
    }

    public Name(String name, PurgomalumClient purgomalumClient) {
        if(Objects.isNull(name) || purgomalumClient.containsProfanity(name)){
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

