package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Price(BigDecimal price) {
        checkPriceIsMinus(price);
        this.price = price;
    }

    protected Price() {
    }

    private void checkPriceIsMinus(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 음수가 될수 없습니다");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }
}
