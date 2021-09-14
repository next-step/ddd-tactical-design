package kitchenpos.menus.tobe.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.common.tobe.domain.Price;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() {
    }

    public MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public Price getTotalPrice() {
        return menuProducts.stream()
            .map(MenuProduct::getMenuProductPrice)
            .reduce(Price.ZERO, Price::add);
    }

}
