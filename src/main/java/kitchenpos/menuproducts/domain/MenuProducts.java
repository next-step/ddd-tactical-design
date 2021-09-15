package kitchenpos.menuproducts.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class MenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {}

    public MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = Collections.unmodifiableList(menuProducts);
    }

    public List<MenuProduct> getMenuProducts() {
        return new ArrayList<>(menuProducts);
    }

    public BigDecimal sumProductPrice() {
        return menuProducts.stream().map(menuProduct -> menuProduct.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, (acc, curr) -> acc.add(curr));
    }
}
