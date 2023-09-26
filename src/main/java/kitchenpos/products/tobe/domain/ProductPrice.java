package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal productPrice;

    public ProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    protected ProductPrice() {
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice price = (ProductPrice) o;
        return Objects.equals(productPrice, price.productPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productPrice);
    }
}
