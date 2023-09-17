package kitchenpos.product.domain;


import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPrice {

    private long value;

    protected ProductPrice() {
    }

    private ProductPrice(final long value) {
        this.value = value;
    }

    public static ProductPrice create(final long value) {
        checkArgument(value >= 0, "price must be greater than and equal to 0. value : %s", value);

        return new ProductPrice(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductPrice that = (ProductPrice) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
