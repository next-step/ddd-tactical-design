package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.Product;
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

    public static class ProductBuilder{
        private ProductName name;
        private ProductPrice price;

        public ProductBuilder() {

        }

        public ProductBuilder name(String name, ProductNamingRule namingRule) {
            this.name = new ProductName(name, namingRule);
            return this;
        }

        public ProductBuilder price(BigDecimal price, ProductPricingRule pricingRule) {
            this.price = new ProductPrice(price, pricingRule);
            return this;
        }

        public TobeProduct build() {
            if(Objects.isNull(name)||Objects.isNull(price)) {
                throw new IllegalArgumentException();
            }
            return new TobeProduct(name, price);
        }
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
}
