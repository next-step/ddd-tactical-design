package kitchenpos.menus.tobe.domain;

import static kitchenpos.global.utils.CollectionUtils.isEmpty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.global.vo.Price;

@Embeddable
public class MenuProducts implements Serializable {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> values = new ArrayList<>();

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
            Price menuProductPrice = menuProduct.getPrice();
            sum = sum.add(menuProductPrice);
        }

        return sum;
    }

    public List<MenuProduct> getValues() {
        return Collections.unmodifiableList(values);
    }
}
