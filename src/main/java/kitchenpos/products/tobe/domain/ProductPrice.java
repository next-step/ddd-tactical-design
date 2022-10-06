package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public ProductPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격이 존재하지 않거나, 가격이 0원 미만입니다.");
        }
        this.price = price;
    }

    public ProductPrice() {

    }

    public BigDecimal getPrice() {
        return price;
    }

}
