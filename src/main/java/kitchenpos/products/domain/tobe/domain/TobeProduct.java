package kitchenpos.products.domain.tobe.domain;

import kitchenpos.support.exception.NamingRuleViolationException;
import kitchenpos.support.exception.PricingRuleViolationException;
import kitchenpos.support.policy.NamingRule;
import kitchenpos.support.policy.PricingRule;
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
        private final ProductId productId;
        private String name;
        private NamingRule namingRule;
        private BigDecimal price;
        private PricingRule pricingRule;

        public Builder() {
            this.productId = new ProductId(UUID.randomUUID());

        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder namingRule(NamingRule namingRule) {
            this.namingRule = namingRule;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder pricingRule(PricingRule pricingRule) {
            this.pricingRule = pricingRule;
            return this;
        }

        public TobeProduct build() {
            if (Objects.isNull(name) || Objects.isNull(namingRule) || !namingRule.checkRule(name)) {
                throw new NamingRuleViolationException();
            }
            if (Objects.isNull(price) || Objects.isNull(pricingRule) || !pricingRule.checkRule(price)) {
                throw new PricingRuleViolationException();
            }
            return new TobeProduct(productId, new ProductName(name), new ProductPrice(price));
        }
    }
}
