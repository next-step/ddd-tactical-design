package kitchenpos.menus.tobe.domain.menu.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Embeddable
@Access(AccessType.FIELD)
public class MenuProducts {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "menu_product", joinColumns = @JoinColumn(name = "menu_id"))
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public List<Long> getMenuProductIds() {
        return menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProducts that = (MenuProducts) o;
        return (menuProducts.containsAll(that.menuProducts) && that.menuProducts.containsAll(menuProducts));
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuProducts);
    }
}
