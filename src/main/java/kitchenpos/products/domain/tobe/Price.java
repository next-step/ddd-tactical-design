package kitchenpos.products.domain.tobe;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Embeddable
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
        if (price.compareTo(BigDecimal.ZERO) > 1) {
            throw new IllegalArgumentException("price is not zero");
        }
    }

    @Override
    public boolean equals(Object o) {
        return this.priceValue.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(priceValue);
    }
}
