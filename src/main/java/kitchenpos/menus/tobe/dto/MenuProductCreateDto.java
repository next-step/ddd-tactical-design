package kitchenpos.menus.tobe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;

public class MenuProductCreateDto {
    private UUID productId;
    private Long quantity;

    @JsonCreator
    public MenuProductCreateDto(UUID productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
