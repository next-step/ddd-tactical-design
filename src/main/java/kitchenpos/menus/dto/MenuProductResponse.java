package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductId;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuProductResponse {

    private MenuProductId id;
    private long quantity;
    private UUID productId;

    public MenuProductResponse() {
    }

    public MenuProductResponse(MenuProductId id, UUID productId, long quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static MenuProductResponse fromEntity(MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.getId(),
                menuProduct.getProductId(),
                menuProduct.getQuantityValue()
        );
    }

    public static List<MenuProductResponse> fromEntities(MenuProducts menuProducts) {
        return menuProducts.getValues()
                .stream()
                .map(MenuProductResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public MenuProductId getId() {
        return id;
    }

    public void setId(MenuProductId id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
