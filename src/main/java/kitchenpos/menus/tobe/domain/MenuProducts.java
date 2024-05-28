package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
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
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {}

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public void checkNotLessThenMenuPrice(BigDecimal menuPrice) {
        if (isLessThenMenuPrice(menuPrice)) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isLessThenMenuPrice(BigDecimal menuPrice) {
        BigDecimal sumResult  = calculateTotalPrice();
        return menuPrice.compareTo(sumResult) > 0;
    }

    public BigDecimal calculateTotalPrice() {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.totalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
