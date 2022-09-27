package kitchenpos.menus.tobe.domain.vo;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class MenuGroupName extends ValueObject {

    @Column(name = "name", nullable = false)
    private String value;

    protected MenuGroupName() {
    }

    public MenuGroupName(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
