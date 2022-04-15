package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.NullAndNegativePriceException;
import kitchenpos.support.domain.Value;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class ProductPrice extends Value {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private BigDecimal price;

    protected ProductPrice() {/*no-op*/}

    public ProductPrice(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(BigDecimal price) {
        if (price == null || price.compareTo(ZERO) < 0) {
            throw new NullAndNegativePriceException();
        }
    }

}
