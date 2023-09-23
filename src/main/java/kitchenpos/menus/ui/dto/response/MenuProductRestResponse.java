package kitchenpos.menus.ui.dto.response;

import java.util.UUID;

public class MenuProductRestResponse {

    final private long seq;
    final private long quantity;
    final private UUID productId;

    public MenuProductRestResponse(long seq, UUID productId, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
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
