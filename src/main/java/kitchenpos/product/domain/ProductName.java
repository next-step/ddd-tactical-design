package kitchenpos.product.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Objects;
import javax.persistence.Embeddable;
import kitchenpos.support.vo.Name;

@Embeddable
public class ProductName {

    private String value;

    protected ProductName() {
    }

    private ProductName(final Name name) {
        this.value = name.getValue();
    }

    static ProductName create(final Name name) {
        checkArgument(name != null, "name must be not null. value: %s", name);

        return new ProductName(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductName that = (ProductName) o;
        return Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
