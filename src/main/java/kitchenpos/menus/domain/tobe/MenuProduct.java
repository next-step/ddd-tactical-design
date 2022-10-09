package kitchenpos.menus.domain.tobe;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.products.domain.Product;

public class MenuProduct {
    private Long seq;
    private UUID productId;
    private BigDecimal price;
    private long quantity;

    public MenuProduct(final Long seq, final UUID productId, final BigDecimal price, final long quantity) {
        this.seq = seq;
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("product is required");
        }
        this.productId = productId;
        this.price = price;
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must bigger than or equals 0");
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

        MenuProduct that = (MenuProduct) o;

        if (quantity != that.quantity) {
            return false;
        }
        if (!Objects.equals(seq, that.seq)) {
            return false;
        }
        if (!Objects.equals(productId, that.productId)) {
            return false;
        }
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (int) (quantity ^ (quantity >>> 32));
        return result;
    }
}
