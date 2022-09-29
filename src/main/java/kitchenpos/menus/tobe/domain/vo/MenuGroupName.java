package kitchenpos.menus.tobe.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class MenuGroupName implements ValueObject {

    private Name value;

    protected MenuGroupName() {
    }

    public MenuGroupName(Name value) {
        this.value = value;
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
