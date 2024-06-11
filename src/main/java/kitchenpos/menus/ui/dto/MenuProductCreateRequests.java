package kitchenpos.menus.ui.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.products.domain.tobe.Product;

public class MenuProductCreateRequests {

    private List<MenuProductCreateRequest> menuProducts;

    public MenuProductCreateRequests(List<MenuProductCreateRequest> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.menuProducts = menuProducts;
    }

    public List<UUID> getProductIds() {
        return menuProducts.stream()
                .map(MenuProductCreateRequest::getProductId)
                .toList();
    }

    public void validateMenuProducts(List<Product> products) {
        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException();
        }
    }

    public List<MenuProductCreateRequest> getMenuProducts() {
        return menuProducts;
    }
}
