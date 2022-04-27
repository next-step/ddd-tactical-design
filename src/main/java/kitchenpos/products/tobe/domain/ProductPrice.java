package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.NullAndNegativePriceException;
import kitchenpos.support.domain.Value;

import javax.persistence.Embeddable;

@Embeddable
public class ProductPrice extends Value {
    private static final int ZERO = 0;

    private int price;

    protected ProductPrice() {/*no-op*/}

    public ProductPrice(int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        if (isNegativeAndNull(price)) throw new NullAndNegativePriceException();
    }

    private boolean isNegativeAndNull(int price) {
        return price < ZERO;
    }

}
