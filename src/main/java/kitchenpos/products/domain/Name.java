package kitchenpos.products.domain;

import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
@Access(AccessType.FIELD)
public class Name {
    @Column(name = "name", nullable = false)
    private String value;

    public Name(final String name, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(purgomalumClient)) {
            throw new IllegalArgumentException();
        }
        this.value = name;
    }

    protected Name() {}

    public String value() {
        return this.value;
    }
}
