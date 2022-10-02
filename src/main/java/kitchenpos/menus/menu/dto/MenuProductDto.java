package kitchenpos.menus.menu.dto;

import kitchenpos.menus.menu.tobe.domain.vo.Quantity;

import java.util.UUID;

public class MenuProductDto {

    private UUID productId;
    private Long quantity;

    public MenuProductDto(UUID productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Quantity toQuantity() {
        return Quantity.valueOf(quantity);
    }
}
