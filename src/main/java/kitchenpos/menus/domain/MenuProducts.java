package kitchenpos.menus.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class MenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public MenuProducts() {
    }

    public void addAll(List<MenuProduct> menuProducts) {
        menuProducts
                .forEach(menuProduct -> add(menuProduct));
        this.menuProducts = menuProducts;
    }

    private void add(MenuProduct menuProduct) {
        if (!this.menuProducts.contains(menuProduct)) {
            menuProducts.add(menuProduct);
        }
    }

    public List<MenuProduct> get() {
        return Collections.unmodifiableList(menuProducts);
    }
}
