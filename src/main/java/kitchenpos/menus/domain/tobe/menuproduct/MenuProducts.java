package kitchenpos.menus.domain.tobe.menuproduct;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import kitchenpos.menus.domain.tobe.menu.MenuPrice;

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

    public MenuProducts(List<MenuProduct> menuProducts, MenuPrice price) {
        validateMenuProducts(menuProducts);
        this.menuProducts = menuProducts;
        validatePrice(price);
    }

    private void validateMenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public void validatePrice(MenuPrice price) {
        if (isOverThanProductSumPrice(price)) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isOverThanProductSumPrice(MenuPrice price) {
        return price.isOver(calculateProductSumPrice(menuProducts));
    }

    private BigDecimal calculateProductSumPrice(List<MenuProduct> menuProducts) {
        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : menuProducts) {
            sum = sum.add(menuProduct.calculateSum());
        }
        return sum;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
