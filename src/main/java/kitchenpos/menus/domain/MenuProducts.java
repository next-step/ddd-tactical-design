package kitchenpos.menus.domain;

import kitchenpos.menus.domain.exception.InvalidMenuProductsException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts  {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    List<MenuProduct> menuProducts;

    public MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new InvalidMenuProductsException();
        }
        this.menuProducts = menuProducts;
    }

    public int size() {
        return menuProducts.size();
    }

    public List<UUID> getProductIds() {
        return menuProducts.stream()
                .map(MenuProduct::getId)
                .collect(Collectors.toList());
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public BigDecimal totalAmount() {
        return menuProducts.stream()
                .map(MenuProduct::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
