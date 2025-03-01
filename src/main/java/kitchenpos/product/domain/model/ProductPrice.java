package kitchenpos.product.domain.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.product.domain.exception.ProductPriceException;

@Embeddable
public record ProductPrice(BigDecimal price) {

    public static ProductPrice of(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductPriceException();
        }
        return new ProductPrice(price);
    }

    public BigDecimal get() {
        return price;
    }

    public ProductPrice multiply(long quantity) {
        return new ProductPrice(price.multiply(BigDecimal.valueOf(quantity)));
    }
}
