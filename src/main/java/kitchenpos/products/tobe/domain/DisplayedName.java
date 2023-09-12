package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    @Column(name = "name", nullable = false)
    private String value;

    protected DisplayedName() {}

    public DisplayedName(final String name, PurgomalumClient purgomalumClient) {
        validate(name, purgomalumClient);
        this.value = name;
    }

    private void validate(final String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

    public String getValue() {
        return value;
    }
}
