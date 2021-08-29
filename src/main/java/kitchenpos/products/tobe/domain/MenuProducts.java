package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Embeddable
public class MenuProducts {
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public MenuProducts() {
    }

    public MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public Price calculateTotalPrice() {
        return menuProducts.stream()
                .map(MenuProduct::calculatePrice)
                .reduce(new Price(BigDecimal.ZERO), Price::add);
    }

    public boolean hasProduct(final UUID productId) {
        return menuProducts.stream()
                .anyMatch(menuProduct -> menuProduct.hasProduct(productId));
    }
}
