package kitchenpos.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfanityFilteredName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProfanityFilteredName() {
    }

    protected ProfanityFilteredName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return this.name;
    }
}
