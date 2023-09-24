package kitchenpos.menus.application.menu.dto;

import kitchenpos.menus.domain.menu.MenuProduct;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuProductResponse {

    private final UUID productId;
    private final long quantity;

    public MenuProductResponse(final UUID productId, final long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static List<MenuProductResponse> ofList(final List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(menuProduct -> new MenuProductResponse(menuProduct.getProductId(), menuProduct.getQuantity()))
                .collect(Collectors.toList());
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
