package kitchenpos.menus.tobe.domain.menu;

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

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    public List<MenuProduct> getMenuProducts() {
        return new ArrayList<>(menuProducts);
    }

    Price computeMenuProductsPriceSum() {
        return menuProducts.stream()
            .map(menuProduct -> menuProduct.computePriceSum())
            .reduce(Price.ZERO, Price::add);
    }

}
