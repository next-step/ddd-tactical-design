package kitchenpos.domain.menu.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.domain.support.Price;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Embeddable
class MenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        validateMenuProducts(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validateMenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("menuProducts는 Null이거나 빈 항목이면 안됩니다.");
        }
    }

    public Price getTotalPrice() {
        return new Price(menuProducts.stream()
                .map(MenuProduct::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
