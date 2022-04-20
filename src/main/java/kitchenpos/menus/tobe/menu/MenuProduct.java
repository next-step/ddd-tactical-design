package kitchenpos.menus.tobe.menu;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class MenuProduct {
    private final UUID productId;
    private final long quantity;
    private final BigDecimal productPrice;

    public MenuProduct(UUID productId, long quantity, BigDecimal productPrice) {
        if (quantity < 0) {
            throw new IllegalArgumentException("상품 수량은 0개 이상이어야 합니다.");
        }
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public long amount() {
        return productPrice.intValue() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProduct that = (MenuProduct) o;

        if (quantity != that.quantity) return false;
        if (!Objects.equals(productId, that.productId)) return false;
        return Objects.equals(productPrice, that.productPrice);
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (int) (quantity ^ (quantity >>> 32));
        result = 31 * result + (productPrice != null ? productPrice.hashCode() : 0);
        return result;
    }
}
