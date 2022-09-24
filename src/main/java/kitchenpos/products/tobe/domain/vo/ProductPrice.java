package kitchenpos.products.tobe.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.tobe.domain.exception.MinimumProductPriceException;
import kitchenpos.products.tobe.domain.exception.NullProductPriceException;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class ProductPrice implements Serializable {

    @Column(name = "price", nullable = false)
    @ColumnDefault("0")
    private BigDecimal value;

    protected ProductPrice() {
    }

    public ProductPrice(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new NullProductPriceException();
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new MinimumProductPriceException();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPrice price = (ProductPrice) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
