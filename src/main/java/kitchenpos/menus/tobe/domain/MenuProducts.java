package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuException;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (isNullOrEmpty(menuProducts)) {
            throw new MenuException(MenuErrorCode.MENU_PRODUCTS_IS_NOT_EMPTY_OR_NULL);
        }
        if (isQuantityNegative(menuProducts)) {
            throw new MenuException(MenuErrorCode.QUANTITY_IS_NEGATIVE);
        }
        this.menuProducts = menuProducts;
    }

    private boolean isQuantityNegative(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
            .map(menuProduct -> menuProduct.getQuantity())
            .anyMatch(quantity -> quantity < 0);
    }

    public boolean isNullOrEmpty(List<MenuProduct> menuProducts) {
        return Objects.isNull(menuProducts) || menuProducts.isEmpty();
    }

    public List<MenuProduct> getList() {
        return menuProducts;
    }

    public boolean checkEqualsSize(int productSize) {
        return productSize != menuProducts.size();

    }

}
