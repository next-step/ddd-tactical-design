package kitchenpos.menus.domain;

import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;


import javax.persistence.*;
import java.math.BigDecimal;
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
    private List<TobeMenuProduct> menuProducts;

    public MenuProducts(List<TobeMenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    protected MenuProducts() {

    }

    public BigDecimal getSumOfMenuProductAmount() {
        BigDecimal sum = BigDecimal.ZERO;
        for (TobeMenuProduct menuProduct : menuProducts) {
            BigDecimal calculateAmount = menuProduct.calculateAmount();
            sum = sum.add(calculateAmount);
        }
        return sum;
    }

    public List<TobeMenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
