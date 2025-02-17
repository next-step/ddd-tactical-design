package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice() {}

    private ProductPrice(BigDecimal price) {
        this.price = price;
    }

    public static ProductPrice from(BigDecimal price) {
        validate(price);
        return new ProductPrice(price);
    }

    private static void validate(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }
}
