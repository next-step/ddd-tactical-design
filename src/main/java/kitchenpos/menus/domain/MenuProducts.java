package kitchenpos.menus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.menus.exception.MenuExceptionMessage.EMPTY_MENU_PRODUCT;
import static kitchenpos.menus.exception.MenuExceptionMessage.MENU_PRICE_MORE_PRODUCTS_SUM;

@Embeddable
public class MenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_MENU_PRODUCT);
        }
        this.menuProducts = menuProducts;
    }


    public static MenuProducts of(List<MenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }

    public static MenuProducts create(List<MenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }

    public void validateMenuPrice(Price targetMenuPrice) {
        Price sumOfmenuProductPrice = menuProducts.stream()
                .map(MenuProduct::calculateTotalPrice)
                .reduce(Price.ZERO, Price::plus);

        if (targetMenuPrice.isGreaterThan(sumOfmenuProductPrice)) {
            throw new IllegalArgumentException(MENU_PRICE_MORE_PRODUCTS_SUM);
        }
    }

    @JsonIgnore
    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    @JsonIgnore
    public List<UUID> getMenuProductIds() {
        return menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuProducts);
    }

}
