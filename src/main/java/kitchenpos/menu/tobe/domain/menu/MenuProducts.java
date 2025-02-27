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
    private List<MenuProduct> products;

    public static MenuProducts from(MenuProduct... products) {
        return new MenuProducts(Arrays.asList(products));
    }

    public static MenuProducts from(List<MenuProduct> products) {
        return new MenuProducts(products);
    }

    private MenuProducts(List<MenuProduct> products) {
        if (Objects.isNull(products) || products.isEmpty()) {
            throw new MenuException(MENU_PRODUCTS_EMPTY);
        }
        this.products = new ArrayList<>(products);
    }

    protected MenuProducts() {}

    public boolean isTotalPriceLessThanOrEqualTo(MenuPrice price) {
        return calculateTotalPrice().compareTo(price.getValue()) <= 0;
    }

    public Long calculateTotalPrice() {
        return products.stream()
                .mapToLong(MenuProduct::totalPrice)
                .sum();
    }

    public List<MenuProduct> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public int getProductCount() {
        return products.size();
    }

    public boolean containsProduct(UUID productId) {
        return products.stream()
                .anyMatch(product -> product.getProductId().equals(productId));
    }

    public MenuProduct findProduct(UUID productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new MenuException(MENU_PRODUCT_NOT_FOUND));
    }

    public boolean hasEnoughQuantityOf(UUID productId, int requiredQuantity) {
        return findProduct(productId).getQuantity() >= requiredQuantity;
    }

    public MenuProduct getProduct(UUID uuid) {
        return products.stream()
                .filter(it -> it.getProductId().equals(uuid))
                .findFirst()
                .orElseThrow();
    }
}
