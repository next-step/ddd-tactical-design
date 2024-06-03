package kitchenpos.products.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    @Column(name = "name", nullable = false)
    private String name;

    public Name() {
    }

    public Name(String name) {
        if(Objects.isNull(name)){
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }
}

