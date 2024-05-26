package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayName displayName;

    @Embedded
    private Price price;

    public Product(DisplayName displayName, Price price) {
        this.displayName = displayName;
        this.price = price;
        this.id = UUID.randomUUID();
    }

    public Product() {

    }

    public DisplayName getDisplayName() {
        return displayName;
    }

    public Price getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }

    public Product changePrice(final Price price) {
        this.price = price;
        return this;
    }

}
