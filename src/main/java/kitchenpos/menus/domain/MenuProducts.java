package kitchenpos.menus.domain;

import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
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
    private List<TobeMenuProduct> menuProducts;

    public MenuProducts(List<TobeMenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    protected MenuProducts() {

    }

    public BigDecimal getSumOfMenuProductAmount() {
        BigDecimal sum = BigDecimal.ZERO;
        for (TobeMenuProduct menuProduct : menuProducts) {
            BigDecimal calculateAmount = menuProduct.calculateAmount();
            sum = sum.add(calculateAmount);
        }
        return sum;
    }

    public List<TobeMenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public TobeMenuProduct get(int index) {
        return menuProducts.get(index);
    }

    public boolean isEmpty() {
        return menuProducts.isEmpty();
    }

    public int size() {
        return menuProducts.size();
    }

    public List<ProductId> getProductIds() {
        return menuProducts.stream()
                .map(TobeMenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProducts that = (MenuProducts) o;

        return menuProducts != null ? menuProducts.equals(that.menuProducts) : that.menuProducts == null;
    }

    @Override
    public int hashCode() {
        return menuProducts != null ? menuProducts.hashCode() : 0;
    }
}
