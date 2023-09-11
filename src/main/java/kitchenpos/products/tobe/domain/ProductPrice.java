package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public ProductPrice(BigDecimal price) {
        checkPriceIsMinus(price);
        this.price = price;
    }

    protected ProductPrice() {
    }

    private void checkPriceIsMinus(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 음수가 될수 없습니다");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPrice)) {
            return false;
        }
        ProductPrice productPrice1 = (ProductPrice) o;
        return Objects.equals(price, productPrice1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
