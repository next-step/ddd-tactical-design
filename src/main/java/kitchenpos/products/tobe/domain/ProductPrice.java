package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.exception.message.ProductPriceException;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

import static kitchenpos.products.tobe.exception.message.ProductErrorCode.PRICE_IS_GREATER_THAN_EQUAL_ZERO;

@Embeddable
public class ProductPrice {
    private BigDecimal price;

    protected ProductPrice() {

    }

    public ProductPrice(BigDecimal price) {
        if (isLessThanZero(price)) {
            throw new ProductPriceException(PRICE_IS_GREATER_THAN_EQUAL_ZERO);
        }
        this.price = price;
    }

    private boolean isLessThanZero(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public ProductPrice add(ProductPrice inputPrice) {
        BigDecimal add = price.add(inputPrice.price);
        return new ProductPrice(add);
    }
}


