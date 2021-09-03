package kitchenpos.products.tobe.domain.model;

import java.math.BigDecimal;
import javax.persistence.Embeddable;

@Embeddable
public class Price {

    private static final BigDecimal PRICE_TRESHHOLD = BigDecimal.ZERO;
    private BigDecimal price = BigDecimal.ZERO;

    public Price() {
    }

    public Price(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price는 Null이 될 수 없습니다.");
        }

        if (price.compareTo(PRICE_TRESHHOLD) < 0) {
            throw new IllegalArgumentException("Price는 반드시 0보다 커야 합니다.");
        }
    }
}
