package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "product")
@Entity(name = "tobeProduct")
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayName displayName;

    @Embedded
    private Price price;

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

    public Product(String displayName, Price price, DisplayNamePolicy displayNamePolicy) {
        this.displayName = new DisplayName(displayName, displayNamePolicy);
        this.price = price;
        this.id = UUID.randomUUID();
    }

    protected Product() {
    }

}
