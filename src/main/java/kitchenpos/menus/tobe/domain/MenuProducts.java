package kitchenpos.menus.tobe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kitchenpos.menus.application.MenuProductCreateRequest;

import javax.persistence.*;
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
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<NewMenuProduct> newMenuProducts;

    public MenuProducts() {}

    public MenuProducts(List<NewMenuProduct> newMenuProductList) {
        this.newMenuProducts = newMenuProductList;
    }

    public static MenuProducts create(List<MenuProductCreateRequest> requests) {
        List<NewMenuProduct> newMenuProductList = requests.stream()
                .map(req -> NewMenuProduct.create(req.getProductId(), req.getQuantity()))
                .collect(Collectors.toList());
        return new MenuProducts(newMenuProductList);
    }

    public static MenuProducts of(List<NewMenuProduct> newMenuProductList) {
        return new MenuProducts(newMenuProductList);
    }

    @JsonIgnore
    public List<NewMenuProduct> getMenuProducts() {
        return newMenuProducts;
    }

    @JsonIgnore
    public List<UUID> getMenuProductIds() {
        return newMenuProducts.stream()
                .map(NewMenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(newMenuProducts, that.newMenuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newMenuProducts);
    }
}
