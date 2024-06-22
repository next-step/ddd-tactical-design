package kitchenpos.menus.application.dto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.products.domain.tobe.Product;

public class MenuProductCreateRequests {

    private final List<MenuProductCreateRequest> menuProducts;

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

    public List<MenuProduct> toMenuProducts(List<Product> products) {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.to(findProduct(products, menuProduct)))
                .toList();
    }

    private Product findProduct(List<Product> products, MenuProductCreateRequest menuProduct) {
        return products.stream()
                .filter(product -> product.getId().equals(menuProduct.getProductId()))
                .findFirst().orElseThrow((NoSuchElementException::new));
    }

    public List<MenuProductCreateRequest> getMenuProducts() {
        return menuProducts;
    }
}
