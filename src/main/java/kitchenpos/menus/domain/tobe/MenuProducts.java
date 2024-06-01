package kitchenpos.menus.domain.tobe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

    public MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public BigDecimal totalAmount(){
        return menuProducts.stream()
                .map(MenuProduct::amount)
                .reduce(BigDecimal::add).get();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void changeMenuProductsPrice(final UUID productId, final BigDecimal price){
        this.menuProducts.stream()
                .filter(a -> productId.equals(a.getProductId()))
                .forEach(a -> a.changePrice(price));
    }

    public boolean isExpensiveToPrice(final Price price) {
        if (price.comparePrice(this.totalAmount()) >= 1) {
            return true;
        }

        return false;
    }

}
