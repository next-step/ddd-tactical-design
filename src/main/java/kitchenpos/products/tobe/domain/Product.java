package kitchenpos.products.tobe.domain;


import kitchenpos.shared.event.ProductPriceChangeEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product extends AbstractAggregateRoot<Product> {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    public Product(ProductName productName, ProductPrice productPrice) {
        this.id = UUID.randomUUID();
        this.name = productName;
        this.price = productPrice;
    }

    public Product() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void changePrice(final ProductPrice productPrice) {
        this.price = productPrice;
        registerEvent(new ProductPriceChangeEvent(this.id, this.getPrice()));
    }
}
