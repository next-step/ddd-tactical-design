package kitchenpos.products.domain.tobe;

import java.math.BigDecimal;
import java.util.Optional;

public class Price {
    private BigDecimal priceValue;

    protected Price() {
    }

    private Price(BigDecimal price) {
        this.validate(price);
        this.priceValue = price;
    }

    public static final Price createPrice(BigDecimal price) {
        return new Price(price);
    }

    public Price changePrice(Price price) {
        return new Price(price.priceValue);
    }

    private void validate(BigDecimal price) {
        if (Optional.ofNullable(price).isEmpty() || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price is not zero");
        }
    }

    public BigDecimal getPriceValue() {
        return priceValue;
    }
}
