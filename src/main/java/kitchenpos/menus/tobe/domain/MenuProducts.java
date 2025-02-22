package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductsException;
import kitchenpos.products.tobe.domain.ProductId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> products = new ArrayList<>();

    protected MenuProducts() {
    }

    public MenuProducts(MenuProduct... products) {
        this(Arrays.stream(products).toList());
    }

    public MenuProducts(List<MenuProduct> products) {
        if (Objects.isNull(products) || products.isEmpty()) {
            throw new InvalidMenuProductsException("메뉴 상품은 반드시 입력되어야 합니다");
        }
        this.products = products;
    }

    public boolean containsProduct(ProductId productId) {
        return products.stream()
                .anyMatch(product -> productId.equals(product.getProductId()));
    }

    public Price totalPrice() {
        return products.stream()
                .map(MenuProduct::amount)
                .reduce(Price.ZERO(), Price::add);
    }

    public List<ProductId> productIds() {
        return products.stream()
                .map(MenuProduct::getProductId)
                .toList();
    }

    public boolean isSizeMismatch(int productSize) {
        return products.size() != productSize;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(products);
    }
}
