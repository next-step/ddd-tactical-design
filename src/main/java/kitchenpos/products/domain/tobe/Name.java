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
}

