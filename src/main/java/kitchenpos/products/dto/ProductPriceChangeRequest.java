package kitchenpos.products.dto;

import kitchenpos.products.domain.tobe.domain.policy.ProductPricingRule;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import java.math.BigDecimal;

public class ProductPriceChangeRequest {
    private BigDecimal price;
    private ProductId productId;
    private ProductPricingRule productPricingRule;

    public ProductPriceChangeRequest(ProductId productId, BigDecimal price, ProductPricingRule productPricingRule) {
        this.price = price;
        this.productId = productId;
        this.productPricingRule = productPricingRule;
    }

    public ProductPriceChangeRequest() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public ProductPricingRule getProductPricingRule() {
        return productPricingRule;
    }

    public void setProductPricingRule(ProductPricingRule productPricingRule) {
        this.productPricingRule = productPricingRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPriceChangeRequest that = (ProductPriceChangeRequest) o;

        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        return productPricingRule != null ? productPricingRule.equals(that.productPricingRule) : that.productPricingRule == null;
    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (productPricingRule != null ? productPricingRule.hashCode() : 0);
        return result;
    }
}
