package kitchenpos.products.domain.tobe.domain.vo;

import kitchenpos.products.exception.IllegalProductPricingPolicy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    @Column(name="price")
    private BigDecimal price;

    public ProductPrice(BigDecimal price) {
        checkProductPricingPolicy(price);
        this.price = price;
    }

    protected ProductPrice() {

    }

    public BigDecimal getValue() {
        return price;
    }

    private void checkProductPricingPolicy(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalProductPricingPolicy("잘못된 상품 금액입니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPrice that = (ProductPrice) o;

        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        return price != null ? price.hashCode() : 0;
    }
}
