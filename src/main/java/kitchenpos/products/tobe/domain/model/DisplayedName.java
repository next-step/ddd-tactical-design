package kitchenpos.products.tobe.domain.model;

import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {

    private String name;

    private DisplayedName() {
    }

    public DisplayedName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
