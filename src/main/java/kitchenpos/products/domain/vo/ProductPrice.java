package kitchenpos.products.domain.vo;

import kitchenpos.products.domain.exception.InvalidProductPriceException;
import kitchenpos.support.ValueObject;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class ProductPrice extends ValueObject {
    private BigDecimal price;

    public ProductPrice() {

    }

    public ProductPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductPriceException();
        }
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductPrice changePrice(BigDecimal price) {
        return new ProductPrice(price);
    }
}
