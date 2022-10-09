package kitchenpos.eatinorders.domain.tobe;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class OrderLineItem {
    private Long seq;
    private UUID menuId;

    private BigDecimal price;

    private long quantity;

    public OrderLineItem(
        final Long seq,
        final BigDecimal price,
        final UUID menuId,
        final boolean displayed,
        final BigDecimal menuPrice,
        final long quantity) {

        this.seq = seq;
        if (Objects.isNull(menuId)) {
            throw new IllegalArgumentException("menu can not empty");
        }
        this.menuId = menuId;
        if (!price.equals(menuPrice)) {
            throw new IllegalArgumentException("price of Menu and OrderLineItem Price dose not matched");
        }

        this.price = price;
        if (!displayed) {
            throw new IllegalArgumentException("if menu is hide, can not create OrderLineItem");
        }
        this.quantity = quantity;
    }

    public BigDecimal totalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderLineItem that = (OrderLineItem) o;

        if (quantity != that.quantity) {
            return false;
        }
        if (!Objects.equals(seq, that.seq)) {
            return false;
        }
        if (!Objects.equals(menuId, that.menuId)) {
            return false;
        }
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (int) (quantity ^ (quantity >>> 32));
        return result;
    }
}
