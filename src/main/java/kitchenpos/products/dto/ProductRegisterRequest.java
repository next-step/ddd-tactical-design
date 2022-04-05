package kitchenpos.products.dto;

import kitchenpos.common.policy.NamingRule;
import kitchenpos.common.policy.PricingRule;

import java.math.BigDecimal;

public class ProductRegisterRequest {
    private String name;
    private BigDecimal price;
    private PricingRule productPricingRule;
    private NamingRule productNamingRule;

    public ProductRegisterRequest(
            String name,
            NamingRule productNamingRule,
            BigDecimal price,
            PricingRule productPricingRule
    ) {
        this.name = name;
        this.price = price;
        this.productPricingRule = productPricingRule;
        this.productNamingRule = productNamingRule;
    }

    public ProductRegisterRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PricingRule getProductPricingRule() {
        return productPricingRule;
    }

    public void setProductPricingRule(PricingRule productPricingRule) {
        this.productPricingRule = productPricingRule;
    }

    public NamingRule getProductNamingRule() {
        return productNamingRule;
    }

    public void setProductNamingRule(NamingRule productNamingRule) {
        this.productNamingRule = productNamingRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductRegisterRequest that = (ProductRegisterRequest) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (productPricingRule != null ? !productPricingRule.equals(that.productPricingRule) : that.productPricingRule != null)
            return false;
        return productNamingRule != null ? productNamingRule.equals(that.productNamingRule) : that.productNamingRule == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (productPricingRule != null ? productPricingRule.hashCode() : 0);
        result = 31 * result + (productNamingRule != null ? productNamingRule.hashCode() : 0);
        return result;
    }
}


