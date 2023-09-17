package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.ProductId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuProductResponse {

    private long seq;
    private long quantity;
    private UUID productId;

    public MenuProductResponse() {
    }

    public MenuProductResponse(long seq, ProductId productId, long quantity) {
        this.seq = seq;
        this.productId = productId.getValue();
        this.quantity = quantity;
    }

    public static MenuProductResponse fromEntity(MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.getSeq(),
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

    public long getSeq() {
        return seq;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

}
