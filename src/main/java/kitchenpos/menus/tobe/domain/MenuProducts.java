package kitchenpos.menus.tobe.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import kitchenpos.common.model.Price;

@Embeddable
public class MenuProducts {

    @ElementCollection
    @CollectionTable(name = "menu_product", joinColumns = @JoinColumn(name = "menu_id"))
    private List<MenuProduct> menuProducts;

    protected MenuProducts(){}

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public List<MenuProduct> getMenuProducts() {
        return new ArrayList<>(menuProducts);
    }

    Price computePriceSum() {
        return menuProducts.stream()
            .map(menuProduct -> menuProduct.priceSum())
            .reduce(Price.ZERO, Price::add);
    }

    public void add(MenuProduct menuProduct) {
        this.menuProducts.add(menuProduct);
    }
}
