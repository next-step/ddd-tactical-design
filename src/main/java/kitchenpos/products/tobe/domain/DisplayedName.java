package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {
    @Column(name = "name", nullable = false)
    private String value;

    protected DisplayedName() {
    }

    public DisplayedName(String name) {
        validate(name);
        this.value = name;
    }

    private void validate(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name 은 null 일 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
