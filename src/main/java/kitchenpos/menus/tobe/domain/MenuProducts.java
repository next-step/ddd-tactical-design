package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProductList;

    protected MenuProducts() {

    }

    public MenuProducts(List<MenuProduct> menuProductList) {
        this.menuProductList = menuProductList;
    }

    public static MenuProducts createMenuProducts(final List<Product> products, final List<MenuProduct> menuProductRequests) {
        validateSize(products, menuProductRequests);
        return getFilteredMenuProducts(products, menuProductRequests);
    }

    private static MenuProducts getFilteredMenuProducts(final List<Product> products, final List<MenuProduct> menuProductRequests) {
        final List<MenuProduct> menuProductList = menuProductRequests.stream()
            .map(menuProductRequest -> {
                final long quantity = menuProductRequest.getQuantity();
                if (quantity < 0) {
                    throw new IllegalArgumentException();
                }
                final Product product = products.stream()
                    .filter(p -> p.getId().equals(menuProductRequest.getProductId()))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new);
                return new MenuProduct(product, quantity);
            })
            .collect(Collectors.toList());
        return new MenuProducts(menuProductList);
    }

    private static void validateSize(final List<Product> products, final List<MenuProduct> menuProducts) {
        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException();
        }
    }

    public List<MenuProduct> toMenuProductList() {
        return this.menuProductList;
    }
}
