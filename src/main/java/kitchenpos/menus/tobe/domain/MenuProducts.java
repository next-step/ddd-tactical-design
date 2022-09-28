package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {
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

    public MenuProducts(MenuProduct... values) {
        this(List.of(values));
    }

    public MenuProducts(List<MenuProduct> values) {
        this.values = values;
    }

    public BigDecimal getTotalAmount() {
        return values.stream()
            .map(MenuProduct::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
