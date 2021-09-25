package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.ui.dto.MenuCreateRequest;
import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(final List<MenuProduct> menuProducts) {
        verify(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void verify(final List<MenuProduct> menuProducts) {
        if(Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴는 1개 이상의 상품이 필요합니다.");
        }
    }

    public List<UUID> getProductIds() {
        return menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    public void validateRegistered(List<Product> products) {
        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException();
        }
    }

    public void loadProduct(Product product) {
        menuProducts.stream()
                .filter(menuProduct -> Objects.equals(menuProduct.getProductId(), product.getId()))
                .forEach(menuProduct -> menuProduct.loadProduct(product));
    }

    public boolean isValidAmount(final Amount amount) {
        return amount.isBelowPrice(calculatePrice());
    }

    private BigDecimal calculatePrice() {
        return menuProducts.stream()
                .map(MenuProduct::calculate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuProducts that = (MenuProducts) o;
        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuProducts);
    }
}
