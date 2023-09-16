package kitchenpos.menus.tobe.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String value;

    protected MenuGroupName() {
    }

    public MenuGroupName(final String value) {
        validate(value);
        this.value = value;
    }


    private void validate(final String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getValue() {
        return value;
    }
}
