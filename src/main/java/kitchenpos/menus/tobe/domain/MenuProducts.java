package kitchenpos.menus.tobe.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.menus.tobe.domain.vo.MenuProductAmount;

@Embeddable
public class MenuProducts implements Serializable {

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
        if (Objects.isNull(values) || values.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal sum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : values) {
            MenuProductAmount amount = menuProduct.getAmount();
            sum = sum.add(amount.getValue());
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
