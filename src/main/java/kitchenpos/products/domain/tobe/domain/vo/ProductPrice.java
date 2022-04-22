package kitchenpos.products.domain.tobe.domain.vo;

import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice extends ValueObject<ProductPrice> {

    @Column(name = "price")
    private BigDecimal price;

    public ProductPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    protected ProductPrice() {

    }

    public BigDecimal getValue() {
        return price;
    }

}
