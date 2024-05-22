package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    public Product(UUID id, String name, BigDecimal price, ProductNameValidationService productNameValidationService) {
        this.id = id;
        this.name = new DisplayedName(name, productNameValidationService);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }
}
