package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.tobe.domain.policy.ProductNamingRule;
import kitchenpos.products.domain.tobe.domain.policy.ProductPricingRule;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import kitchenpos.products.domain.tobe.domain.vo.ProductName;
import kitchenpos.products.domain.tobe.domain.vo.ProductPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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

    public ProductPrice changePrice(ProductPrice productPrice, ProductPricingRule rule) {
        this.price = new ProductPrice(productPrice.getValue(), rule);
        return this.price;
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

    public static class ProductBuilder {
        private String name;
        private ProductNamingRule namingRule;
        private BigDecimal price;
        private ProductPricingRule pricingRule;

        public ProductBuilder() {

        }

        public ProductBuilder name(String name, ProductNamingRule namingRule) {
            this.name=name;
            return this;
        }

        public ProductBuilder namingRule(ProductNamingRule namingRule) {
            this.namingRule=namingRule;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price=price;
            return this;
        }

        public ProductBuilder pricingRule(ProductPricingRule pricingRule) {
            this.pricingRule=pricingRule;
            return this;
        }

        public TobeProduct build() {
            if (Objects.isNull(name) || Objects.isNull(price) || Objects.isNull(namingRule)||Objects.isNull(pricingRule)) {
                throw new IllegalArgumentException();
            }
            return new TobeProduct(new ProductName(name, namingRule), new ProductPrice(price, pricingRule));
        }
    }
}
