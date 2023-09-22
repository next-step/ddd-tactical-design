package kitchenpos.menus.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuProducts {

    @ElementCollection
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
        if (menuProducts.size() < 1) {
            throw new IllegalArgumentException(String.format("메뉴에 속한 상품은 1개 이상이어야 합니다. 현재 값: %s", menuProducts.size()));
        }
        this.menuProducts = menuProducts;
    }

    public Price totalPrice() {
        return menuProducts.stream()
                .map(MenuProduct::getPrice)
                .reduce(new Price(0L), Price::sum);
    }

    public void changeProductPrice(UUID productId, Price price) {
        menuProducts.stream().filter(menuProduct -> menuProduct.getProductId().equals(productId))
                .forEach(menuProduct -> menuProduct.changeProductPrice(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuProducts);
    }
}
