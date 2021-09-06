package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private String name;

    protected DisplayedName() {
    }

    public DisplayedName(String name) {
        validationName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validationName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
