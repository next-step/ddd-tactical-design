package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.exception.WrongPriceException;

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
            throw new WrongPriceException();
        }
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
