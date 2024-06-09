package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.*;

import java.util.List;

@Embeddable
public class TobeMenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu_2")
    )
    private List<TobeMenuProduct> menuProducts;

    private TobeMenuProducts() {
    }

    private TobeMenuProducts(List<TobeMenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴에 속한 상품의 수량은 0 이상이어야 한다.");
        }
        this.menuProducts = menuProducts;
    }

    public static TobeMenuProducts of(List<TobeMenuProduct> tobeMenuProducts) {
        return new TobeMenuProducts(tobeMenuProducts);
    }

    int getTotalPrice() {
        return menuProducts.stream()
                .mapToInt(TobeMenuProduct::getTotalPrice)
                .sum();
    }
}
