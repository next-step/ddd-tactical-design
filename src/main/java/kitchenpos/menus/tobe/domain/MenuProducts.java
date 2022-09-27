package kitchenpos.menus.tobe.domain;

import static kitchenpos.global.utils.CollectionUtils.isEmpty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class MenuProducts extends ValueObject {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> values;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> values) {
        validate(values);
        this.values = values;
    }

    private void validate(List<MenuProduct> values) {
        if (isEmpty(values)) {
            throw new IllegalArgumentException();
        }
    }

    public Price sum() {
        Price sum = new Price(BigDecimal.ZERO);
        for (MenuProduct menuProduct : values) {
            sum = sum.add(menuProduct.getPrice());
        }

        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
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
