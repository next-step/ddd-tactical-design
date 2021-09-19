package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    protected MenuProducts() { }

    public MenuProducts(List<TobeMenuProduct> menuProducts) {
        validationMenuProductSize(menuProducts);
        this.menuProducts = menuProducts;
    }

    public BigDecimal sumMenuProductPrice() {
        return menuProducts.stream()
                .map(TobeMenuProduct::menuProductPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public List<TobeMenuProduct> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }

    private void validationMenuProductSize(List<TobeMenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
