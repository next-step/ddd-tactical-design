package kitchenpos.menus.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public MenuProducts() {
    }

    public void addAll(List<MenuProduct> menuProducts) {
        validate(menuProducts);
        menuProducts.forEach(this::add);
    }

    private void validate(List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void add(MenuProduct menuProduct) {
        if (!this.menuProducts.contains(menuProduct)) {
            menuProducts.add(menuProduct);
        }
    }

    public List<MenuProduct> get() {
        return Collections.unmodifiableList(menuProducts);
    }

    public boolean hasTotalPriceLowerThan(BigDecimal price) {
        BigDecimal sum = menuProducts.stream()
            .map(MenuProduct::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return price.compareTo(sum) > 0;
    }
}
