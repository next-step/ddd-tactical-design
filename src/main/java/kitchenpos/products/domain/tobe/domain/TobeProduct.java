package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.tobe.domain.vo.ProductPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class TobeProduct {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    @Embedded
    private ProductPrice price;

    public TobeProduct() {
    }

    public ProductPrice changePrice(ProductPrice productPrice) {
        this.price = new ProductPrice(productPrice.getValue());
        return this.price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

}
