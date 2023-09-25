package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        validateMenuProducts(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validateMenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴 상품은 필수로 존재해야 합니다.");
        }
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public static MenuProducts from(List<MenuProduct> menuProducts, ProductClient productClient) {
        validateMenuProducts(menuProducts, productClient);
        return new MenuProducts(menuProducts);
    }

    public BigDecimal getTotalMenuProductsPrice() {
        return menuProducts.stream().map(menuProduct -> menuProduct.getMenuProductPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void validateMenuProducts(List<MenuProduct> menuProducts, ProductClient productClient) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productClient.findAllByIdIn(
                menuProducts.stream()
                        .map(MenuProduct::getProductId)
                        .collect(Collectors.toList())
        );
        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException();
        }
    }
}
