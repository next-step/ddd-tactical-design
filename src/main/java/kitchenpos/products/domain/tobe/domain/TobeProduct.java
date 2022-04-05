package kitchenpos.products.domain.tobe.domain;

import kitchenpos.common.exception.NamingRuleViolationException;
import kitchenpos.common.exception.PricingRuleViolationException;
import kitchenpos.common.policy.NamingRule;
import kitchenpos.common.policy.PricingRule;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import kitchenpos.products.domain.tobe.domain.vo.ProductName;
import kitchenpos.products.domain.tobe.domain.vo.ProductPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class TobeProduct {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @EmbeddedId
    private ProductId id;

    @Column(name = "name", nullable = false)
    @Embedded
    private ProductName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private ProductPrice price;

    protected TobeProduct() {
    }

    private TobeProduct(ProductName name, ProductPrice price) {
        this.name = name;
        this.price = price;
    }

    private TobeProduct(ProductId id, ProductName name, ProductPrice price) {
        this(name, price);
        this.id = id;
    }

    public TobeProduct changePrice(BigDecimal price, PricingRule rule) {
        rule.checkRule(price);
        this.price = new ProductPrice(price);
        return this;
    }

    public ProductId getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public static class Builder {
        private ProductId productId;
        private String name;
        private NamingRule namingRule;
        private BigDecimal price;
        private PricingRule pricingRule;

        public Builder() {

        }

        public Builder name(String name) {
            this.name=name;
            return this;
        }

        public Builder namingRule(NamingRule namingRule) {
            this.namingRule=namingRule;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price=price;
            return this;
        }

        public Builder pricingRule(PricingRule pricingRule) {
            this.pricingRule=pricingRule;
            return this;
        }

        public TobeProduct build() {
            if (Objects.isNull(name) || Objects.isNull(namingRule)) {
                throw new NamingRuleViolationException();
            }
            if (Objects.isNull(price) || Objects.isNull(pricingRule)) {
                throw new PricingRuleViolationException();
            }
            namingRule.checkRule(name);
            pricingRule.checkRule(price);
            return new TobeProduct(new ProductId(UUID.randomUUID()),new ProductName(name), new ProductPrice(price));
        }
    }
}
