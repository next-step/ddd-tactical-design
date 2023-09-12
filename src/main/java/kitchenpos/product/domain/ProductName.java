package kitchenpos.product.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class ProductName {

    private String value;

    public static ProductName of(final Name name) {
        checkArgument(name != null, "name must be not null. value: %s", name);

        return new ProductName(name);
    }

    public ProductName() {
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


    private ProductName(final Name name) {
        this.value = name.getValue();
    }
}
