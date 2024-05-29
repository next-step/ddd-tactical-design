package kitchenpos.menus.tobe.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import kitchenpos.menus.dto.MenuProductCreateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> values;

    protected MenuProducts() {
    }

    protected MenuProducts(List<MenuProduct> values) {
        this.values = values;
    }

    public static MenuProducts from(List<MenuProduct> values) {
        validateNullOrEmptyList(values);
        return new MenuProducts(values);
    }

    public static MenuProducts from(List<MenuProductCreateRequest> requests, ProductClient productClient) {
        validateValues(requests, productClient);
        return new MenuProducts(
                requests.stream()
                        .map(request -> request.toMenuProduct(productClient))
                        .toList()
        );
    }

    public BigDecimal sumPrice() {
        return values.stream()
                .map(MenuProduct::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MenuProduct> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    private static void validateNullOrEmptyList(List<MenuProduct> values) {
        if (Objects.isNull(values) || values.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateValues(List<MenuProductCreateRequest> requests, ProductClient productClient) {
        validateNullOrEmptyRequestList(requests);
        validateMatchingProductId(requests, productClient);
    }

    private static void validateNullOrEmptyRequestList(List<MenuProductCreateRequest> values) {
        if (Objects.isNull(values) || values.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateMatchingProductId(List<MenuProductCreateRequest> requests, ProductClient productClient) {
        validateNullOrEmptyRequestList(requests);
        List<UUID> productIds = getProductIds(requests);
        int productSize = productClient.countMatchingProductIdIn(productIds);
        if (productSize != requests.size()) {
            throw new IllegalArgumentException();
        }
    }

    private static List<UUID> getProductIds(List<MenuProductCreateRequest> requests) {
        return requests.stream()
                .map(MenuProductCreateRequest::productId)
                .toList();
    }
}
