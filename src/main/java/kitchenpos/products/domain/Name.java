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

    public Name(final String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
        this.value = name;
    }

    protected Name() {}

    public void verifySlang(PurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(this.value)) {
            throw new IllegalArgumentException("cannot be included slang");
        }
    }
}
