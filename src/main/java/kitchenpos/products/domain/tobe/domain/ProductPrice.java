package kitchenpos.products.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;

public class ProductPrice {
    @Column(name = "price", nullable = false)
    private final BigDecimal price;
    private final String currency;

    private ProductPrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    public static ProductPrice of(BigDecimal price) {
        return new ProductPrice(price);
    }

    public static ProductPrice of(long price) {
        return new ProductPrice(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductPrice))
            return false;
        ProductPrice that = (ProductPrice)o;
        return Objects.equals(price, that.price) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, currency);
    }

    private String defaultCurrency() {
        return "Won";
    }

    private void validationOfPrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException();
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isGreaterThan(BigDecimal comparePrice) {
        return price.compareTo(comparePrice) >= 1;
    }

    public boolean isSamePrice(ProductPrice comparePrice) {
        return this.equals(comparePrice);
    }

    public boolean isGreaterThan(long price) {
        return isGreaterThan(BigDecimal.valueOf(price));
    }

    public ProductPrice changePrice(BigDecimal newPrice) {
        return new ProductPrice(newPrice);
    }

    public ProductPrice changePrice(Long newPrice) {
        return new ProductPrice(BigDecimal.valueOf(newPrice));
    }
}
