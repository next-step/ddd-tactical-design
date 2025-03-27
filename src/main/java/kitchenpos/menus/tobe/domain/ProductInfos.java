package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.application.MenuProductRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public record ProductInfos(List<ProductInfo> products) {

    private static MenuProductRequest getMenuProductRequest(List<MenuProductRequest> menuProductRequests, ProductInfo product) {
        return menuProductRequests.stream()
                .filter(menuProductRequest -> menuProductRequest.productId().equals(product.id()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public ProductInfo findById(UUID productId) {
        return products.stream()
                .filter(product -> product.id().equals(productId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public int size() {
        return products.size();
    }

    public BigDecimal getSumsWithRequests(List<MenuProductRequest> menuProductRequests) {
        return products.stream()
                .map(product -> {
                    MenuProductRequest menuProduct = getMenuProductRequest(menuProductRequests, product);
                    return product.price().multiply(BigDecimal.valueOf(menuProduct.quantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getSums(MenuProducts menuProducts) {
        return products.stream()
                .map(product -> {
                    MenuProduct menuProduct = menuProducts.getById(product.id());
                    return product.price().multiply(BigDecimal.valueOf(menuProduct.getQuantity().value()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
