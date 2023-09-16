package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.application.MenuProductCreateRequest;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
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
    private List<MenuProduct> menuProducts;

    public MenuProducts() {}

    public MenuProducts(List<MenuProduct> menuProductList) {
        this.menuProducts = menuProductList;
    }

    public static MenuProducts create(List<MenuProductCreateRequest> requests) {
        List<MenuProduct> menuProductList = requests.stream()
                .map(req ->MenuProduct.of(req.getProductId(), req.getQuantity()))
                .collect(Collectors.toList());
        return new MenuProducts(menuProductList);
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public List<UUID> getMenuProductIds() {
        return menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
    }
}
