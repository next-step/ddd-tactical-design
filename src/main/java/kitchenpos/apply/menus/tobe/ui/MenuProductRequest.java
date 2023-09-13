package kitchenpos.apply.menus.tobe.ui;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class MenuProductRequest {

    @NotNull
    private final Long seq;

    @NotNull
    private final UUID productId;

    private final long quantity;

    @NotNull
    private final UUID menuId;

    public MenuProductRequest(Long seq, UUID productId, long quantity, UUID menuId) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.menuId = menuId;
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }
}
