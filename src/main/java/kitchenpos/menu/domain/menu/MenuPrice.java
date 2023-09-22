package kitchenpos.menu.domain.menu;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class MenuPrice {

    private int value;

    protected MenuPrice() {
    }

    private MenuPrice(final int value) {
        this.value = value;
    }

    public static MenuPrice create(final int value) {
        checkArgument(value >= 0, "price must be greater than and equal to 0. value : %s", value);

        return new MenuPrice(value);
    }

    boolean isGreaterThan(final MenuProducts menuProducts) {
        return menuProducts.totalAmount() < value;
    }

    @Override

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuPrice menuPrice = (MenuPrice) o;
        return value == menuPrice.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public int getValue() {
        return value;
    }
}
