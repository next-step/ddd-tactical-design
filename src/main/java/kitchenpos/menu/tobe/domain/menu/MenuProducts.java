package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.menu.tobe.domain.menu.validate.ProductValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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

    public MenuProducts(List<MenuProduct> menuProducts, BigDecimal menuPrice, ProductValidator productValidator) {
        validateMenuProductsNotEmpty(menuProducts);
        productValidator.validateProductExistence(menuProducts);
        validateMenuPricePolicy(menuPrice, menuProducts);

        this.menuProducts = menuProducts;
    }

    private void validateMenuProductsNotEmpty(List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴에 속한 상품이 비어있습니다.");
        }
    }

    public void validateMenuPricePolicy(BigDecimal menuPrice, List<MenuProduct> menuProducts) {
        BigDecimal totalPrice = menuProducts.stream()
                .map(menuProduct -> menuProduct.getPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (menuPrice.compareTo(totalPrice) > 0) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴에 속한 상품의 가격보다 클 수 없습니다.");
        }
    }

    public void changeMenuProductPrice(UUID productId, BigDecimal price) {
        MenuProduct menuProduct = findMenuProductById(productId);
        if (menuProduct != null) {
            menuProduct.changePrice(price);
            validateMenuPricePolicy(price, menuProducts);
        }
    }

    private MenuProduct findMenuProductById(UUID productId) {
        return menuProducts.stream()
                .filter(menuProduct -> menuProduct.isSameProductId(productId))
                .findFirst()
                .orElse(null);
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProducts that)) {
            return false;
        }

        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuProducts);
    }
}
