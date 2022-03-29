package kitchenpos.products.domain.tobe.domain.vo;

import kitchenpos.products.domain.tobe.domain.policy.ProductPricingRule;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class ProductPrice {

    @Column(name="price")
    private BigDecimal price;

    public ProductPrice(BigDecimal price, ProductPricingRule rule) {
        rule.checkRule(price);
        this.price = price;
    }

    protected ProductPrice() {

    }

    public BigDecimal getValue() {
        return price;
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
