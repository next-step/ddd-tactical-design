package kitchenpos.products.tobe.menu.domain;

import kitchenpos.products.tobe.support.Value;

import javax.persistence.*;
import java.util.List;

@Embeddable
public class MenuProducts extends Value<MenuProducts> {


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;


    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }
}
