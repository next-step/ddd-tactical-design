package kitchenpos.menu.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.Collections;
import java.util.List;
import kitchenpos.menu.domain.entity.MenuProduct;

@Embeddable
public record MenuProducts(
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    List<MenuProduct> menuProducts
) {
    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = List.copyOf(menuProducts); // 불변 리스트 유지
    }

//    public BigDecimal getTotalPrice() {
//        return menuProducts.stream()
//            .map(MenuProduct::)
//            .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }

    public List<MenuProduct> get() {
        return Collections.unmodifiableList(menuProducts);
    }
}
