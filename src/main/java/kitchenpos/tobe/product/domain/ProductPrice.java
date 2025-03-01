package kitchenpos.tobe.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.tobe.product.domain.exception.InvalidProductPriceException;

import java.math.BigDecimal;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal amount;

    protected ProductPrice() {
        this.amount = BigDecimal.ZERO;
    }

    public ProductPrice(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductPriceException();
        }
        this.amount = amount;
    }

    public static ProductPrice of(BigDecimal value) {
        return new ProductPrice(value);
    }

    public static ProductPrice of(long value) {
        return new ProductPrice(BigDecimal.valueOf(value));
    }

    public static ProductPrice of(int value) {
        return new ProductPrice(BigDecimal.valueOf(value));
    }

    public static ProductPrice of(double value) {
        return new ProductPrice(BigDecimal.valueOf(value));
    }

    public static ProductPrice of(String value) {
        return new ProductPrice(new BigDecimal(value));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPrice that)) {
            return false;
        }
        return amount.compareTo(that.amount) == 0;
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

}
