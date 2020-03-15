package kitchenpos.products.tobe.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Access(AccessType.FIELD)
public class ProductPrice {

    private final BigDecimal price;


    public ProductPrice() {
        price = null;
    }

    public ProductPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
