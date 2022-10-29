package kitchenpos.products.tobe.domain.entity;

import kitchenpos.products.tobe.domain.service.ProductMenuPricePolicy;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Product {
    @Column(name = "id")
    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false))
    private ProductName productName;

    @Embedded
    private ProductPrice price;

    public Product(ProductName productName, ProductPrice price) {
        this.id = UUID.randomUUID();
        this.productName = productName;
        this.price = price;
    }

    public void changePrice(ProductPrice price, ProductMenuPricePolicy productMenuPricePolicy) {
        this.price = price;

        productMenuPricePolicy.checkMenuPrice(id);
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return productName;
    }

    public ProductPrice getPrice() {
        return price;
    }

    protected Product() {}
}
