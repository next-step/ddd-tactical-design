package kitchenpos.menus.tobe.domain;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuProduct> menuProducts = new ArrayList<>();


    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public long getTotalPrice() {
        return menuProducts.stream()
            .mapToLong(MenuProduct::getTotalPrice)
            .sum();
    }

    public List<MenuProduct> getMenuProducts() {
        return unmodifiableList(menuProducts);
    }
}
