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
    private ProductName name;

    @Embedded
    private ProductPrice price;

    public Product(ProductName name, ProductPrice price) {
        this.id = UUID.randomUUID();
        this.name = name;
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
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    protected Product() {}
}
