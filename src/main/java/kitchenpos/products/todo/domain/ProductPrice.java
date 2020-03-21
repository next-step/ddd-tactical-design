package kitchenpos.products.todo.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class ProductPrice {
    private BigDecimal price;

    protected ProductPrice() {
    }

    public ProductPrice(BigDecimal price) {
        verifyPrice(price);
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private void verifyPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
