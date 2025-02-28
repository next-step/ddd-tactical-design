package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.common.exception.MenuException;

import java.util.*;

import static kitchenpos.common.exception.ErrorCode.MENU_PRODUCTS_EMPTY;
import static kitchenpos.common.exception.ErrorCode.MENU_PRODUCT_NOT_FOUND;

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

    public static MenuProducts from(MenuProduct... products) {
        return new MenuProducts(Arrays.asList(products));
    }

    public static MenuProducts from(List<MenuProduct> products) {
        return new MenuProducts(products);
    }

    private MenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new MenuException(MENU_PRODUCTS_EMPTY);
        }
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    protected MenuProducts() {}

    public boolean isTotalPriceLessThanOrEqualTo(MenuPrice price) {
        return calculateTotalPrice().compareTo(price.getValue()) <= 0;
    }

    public Long calculateTotalPrice() {
        return menuProducts.stream()
                .mapToLong(MenuProduct::totalPrice)
                .sum();
    }

    public List<MenuProduct> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }

    public int getProductCount() {
        return menuProducts.size();
    }

    public boolean containsProduct(UUID productId) {
        return menuProducts.stream()
                .anyMatch(product -> product.getProductId().equals(productId));
    }

    public MenuProduct findProduct(UUID productId) {
        return menuProducts.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new MenuException(MENU_PRODUCT_NOT_FOUND));
    }

    public boolean hasEnoughQuantityOf(UUID productId, int requiredQuantity) {
        return findProduct(productId).getQuantity() >= requiredQuantity;
    }

    public MenuProduct getProduct(UUID uuid) {
        return menuProducts.stream()
                .filter(it -> it.getProductId().equals(uuid))
                .findFirst()
                .orElseThrow();
    }
}
