package kitchenpos.menus.tobe.application;

import jakarta.persistence.*;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.Money;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }


    public static MenuProducts of(List<MenuProduct> menuProductRequests, Money menuPrice) {

        var menuProducts = new MenuProducts(menuProductRequests);

        if (menuProducts.totalAmount().compareTo(menuPrice) < 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
        }

        return menuProducts;
    }

    public Money totalAmount() {
        return menuProducts.stream()
                .map(MenuProduct::amount)
                .reduce((a, b) -> new Money(a.value().add(b.value())))
                .orElseThrow(RuntimeException::new);
    }

    public List<MenuProduct> values() {
        return Collections.unmodifiableList(menuProducts);
    }

    public void changePrice(UUID productId, Money productPrice) {
        MenuProduct menuProduct = menuProducts.stream()
                .filter(it -> it.getProductId().equals(productId))
                .findAny()
                .orElseThrow(NullPointerException::new);

        menuProduct.changePrice(productPrice);
    }
}
