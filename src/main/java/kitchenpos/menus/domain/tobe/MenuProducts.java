package kitchenpos.menus.domain.tobe;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Embeddable
public class MenuProducts {
    private static final String MENU_PRODUCTS_EMPTY_NOT_ALLOWED = "MenuProducts must have at least one MenuProduct";

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)"
    )
    private List<MenuProduct> values;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> values) {
        validate(values);
        this.values = values;
    }

    public MenuPrice getTotalPrice() {
        return values.stream()
                .map(MenuProduct::getTotalPrice)
                .reduce(MenuPrice.valueOf(0), MenuPrice::add);
    }

    private void validate(List<MenuProduct> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException(MENU_PRODUCTS_EMPTY_NOT_ALLOWED);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProducts)) {
            return false;
        }
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}
