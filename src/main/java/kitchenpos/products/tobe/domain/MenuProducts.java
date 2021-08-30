package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<UUID> getProductIds() {
        return menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    public Price calculateTotalPrice(final Map<UUID, Product> products) {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.calculatePrice(products))
                .reduce(Price.getZero(), Price::add);
    }

    public boolean hasProduct(final UUID productId) {
        return menuProducts.stream()
                .anyMatch(menuProduct -> menuProduct.hasProduct(productId));
    }
}
