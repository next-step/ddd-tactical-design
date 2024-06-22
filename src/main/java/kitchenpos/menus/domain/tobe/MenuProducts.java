package kitchenpos.menus.domain.tobe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import kitchenpos.products.domain.tobe.Product;
import org.jetbrains.annotations.NotNull;

@Embeddable
public class MenuProducts implements Iterable<MenuProduct> {

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

    public MenuProducts(List<MenuProduct> menuProducts, List<Product> products) {
        validateMenuProducts(menuProducts, products);
        this.menuProducts = menuProducts;
    }

    private void validateMenuProducts(List<MenuProduct> menuProducts, List<Product> products) {
        if (menuProducts == null || products == null) {
            throw new IllegalArgumentException();
        }

        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException();
        }

        if (menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal calculateSumPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : menuProducts) {
            sum = sum.add(menuProduct.calculateSum());
        }
        return sum;
    }

    @NotNull
    @Override
    public Iterator<MenuProduct> iterator() {
        return menuProducts.iterator();
    }

    public Stream<MenuProduct> stream() {
        return menuProducts.stream();
    }
}
