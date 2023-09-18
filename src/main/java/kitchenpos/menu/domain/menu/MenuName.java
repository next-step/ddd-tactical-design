package kitchenpos.menu.domain.menu;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Objects;
import javax.persistence.Embeddable;
import kitchenpos.support.vo.Name;

@Embeddable
public class MenuName {

    private String value;

    protected MenuName() {

    }

    private MenuName(final Name name) {
        value = name.getValue();
    }

    static MenuName create(final Name name) {
        checkArgument(name != null, "name must be not null. value: %s", name);

        return new MenuName(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuName menuName = (MenuName) o;
        return Objects.equal(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
