package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.menus.tobe.domain.common.*;

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

    public boolean isLessThenPrice(Price inputPrice) {
        Price menuProductsPrice = totalPrice();
        return menuProductsPrice.isLessThen(inputPrice);
    }

    private Price totalPrice() {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.totalPrice())
                .reduce(new Price(BigDecimal.ZERO), Price::add);
    }
}
