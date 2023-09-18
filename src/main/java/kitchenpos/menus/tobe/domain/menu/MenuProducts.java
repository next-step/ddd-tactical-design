package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private MenuProducts(List<MenuProduct> menuProducts) {
        this.validate(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validate(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public static MenuProducts from(List<MenuProductMaterial> menuProductMaterials, ProductClient productClient) {
        validateMenuProducts(menuProductMaterials, productClient);
        return menuProductMaterials.stream()
                .map(it -> MenuProduct.from(it.getProductId(), it.getQuantity(), productClient))
                .collect(Collectors.collectingAndThen(Collectors.toList(), MenuProducts::new));
    }

    private static void validateMenuProducts(final List<MenuProductMaterial> menuProductMaterials, ProductClient productClient) {
        if (Objects.isNull(menuProductMaterials) || menuProductMaterials.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<UUID> productIds = menuProductMaterials.stream()
                .map(MenuProductMaterial::getProductId)
                .collect(Collectors.toList());
        productClient.validProductIds(productIds);
    }

    public void changeMenuProductPrice(UUID productId, BigDecimal price) {
        for (final MenuProduct menuProduct : this.menuProducts) {
            if (menuProduct.isSameProductId(productId)) {
                menuProduct.changePrice(price);
            }
        }
    }

    public MenuPrice totalAmount() {
        MenuPrice totalAmount = MenuPrice.ZERO;
        for (final MenuProduct menuProduct : this.menuProducts) {
            totalAmount = totalAmount.add(MenuPrice.from(menuProduct.calculateAmount()));
        }
        return totalAmount;
    }

    public Stream<MenuProduct> stream() {
        return this.menuProducts.stream();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
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
