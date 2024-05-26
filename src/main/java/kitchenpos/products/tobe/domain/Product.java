package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductProfanityValidator;
import kitchenpos.products.tobe.domain.event.ProductPriceChangeEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

@Table(name = "product")
@Entity
public class Product extends AbstractAggregateRoot<Product> {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private ProductName name;

    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice price;

    protected Product() {
    }

    public Product(ProductProfanityValidator profanityValidator, String name, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(profanityValidator, name);
        this.price = new ProductPrice(price);
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

    public void changePrice(final BigDecimal price) {
        this.price = new ProductPrice(price);
        registerEvent(new ProductPriceChangeEvent(this.id));
    }
}
