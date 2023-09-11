package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuProductResponse {

    private Long seq;
    private long quantity;
    private UUID productId;

    public MenuProductResponse() {
    }

    public MenuProductResponse(Long seq, UUID productId, long quantity) {
        this.seq = seq;
        this.productId = productId;
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

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
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
