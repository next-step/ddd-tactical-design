package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "Product2")
@Table(name = "product2")
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private CleanName name;

    @Embedded
    private Price price;

    public Product() {}

    public Product(UUID id, String name, BigDecimal price, ProductNameValidationService productNameValidationService) {
        this(id, name, new Price(price),productNameValidationService);
    }

    public Product(UUID id, String name, Price price, ProductNameValidationService productNameValidationService) {
        this.id = id;
        this.name = new CleanName(name, productNameValidationService);
        this.price = price;
    }

    public void changePrice(BigDecimal changedPrice) {
        price = new Price(changedPrice);
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public UUID getId() {
        return id;
    }
}
