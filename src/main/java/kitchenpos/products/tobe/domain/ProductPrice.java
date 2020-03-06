package kitchenpos.products.tobe.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class ProductPrice {
    private final BigDecimal price;

    protected ProductPrice() {
        price = null;
    }

    public ProductPrice(BigDecimal price) {
        validPriceNotNullAndGreaterThanZero(price);

        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void validPriceNotNullAndGreaterThanZero(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
