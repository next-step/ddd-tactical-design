package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.tobe.domain.policy.ProductPricingRule;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import kitchenpos.products.domain.tobe.domain.vo.ProductName;
import kitchenpos.products.domain.tobe.domain.vo.ProductPrice;

import javax.persistence.*;

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

    public TobeProduct() {
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
