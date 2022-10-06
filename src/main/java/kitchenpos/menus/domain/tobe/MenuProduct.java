package kitchenpos.menus.domain.tobe;

import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.products.domain.Product;

public class MenuProduct {
    private Long seq;
    private Product product;
    private long quantity;

    public MenuProduct(final Long seq, final Product product, final long quantity) {
        this.seq = seq;
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("product is required");
        }
        this.product = product;
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must bigger than or equals 0");
        }
        this.quantity = quantity;
    }

    public BigDecimal totalPrice() {
        return product.getPrice()
            .multiply(BigDecimal.valueOf(quantity));
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
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (int) (quantity ^ (quantity >>> 32));
        return result;
    }
}
