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
        validateProductPrice(productPrice);
        this.productPrice = productPrice;
    }

    protected ProductPrice() {

    }

    private void validateProductPrice(BigDecimal productPrice) {
        if (Objects.isNull(productPrice)) {
            throw new IllegalArgumentException("상풍 가격은 필수로 존재해야 합니다.");
        }
        if (productPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품 가격은 0원 이상이어야 합니다.");
        }
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(productPrice, that.productPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productPrice);
    }
}
