package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductId;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.ProductId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuProductResponse {

    private UUID id;
    private long quantity;
    private UUID productId;

    public MenuProductResponse() {
    }

    public MenuProductResponse(MenuProductId id, ProductId productId, long quantity) {
        this.id = id.getValue();
        this.productId = productId.getValue();
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

    public UUID getId() {
        return id;
    }

    public void setId(MenuProductId id) {
        this.id = id.getValue();
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId.getValue();
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
