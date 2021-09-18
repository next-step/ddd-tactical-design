package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProduct {
    private final Long seq;

    private final UUID productId;

    private final long quantity;

    private final Price price;

    public MenuProduct(final Long seq, final UUID productId, final long quantity, final Price price) {
        validateQuantity(quantity);
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    private void validateQuantity(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품의 수량은 0 이상이어야 합니다.");
        }
    }

    public Price getAmount() {
        return price.multiply(BigDecimal.valueOf(quantity));
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

    public Price getPrice() {
        return price;
    }
}
