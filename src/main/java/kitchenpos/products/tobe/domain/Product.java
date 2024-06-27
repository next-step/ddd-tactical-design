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
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    protected Product() {
        // JPA requires a default constructor
    }

    public Product(String name, ProfanitiesChecker profanitiesChecker, Long price) {
        this.id = UUID.randomUUID();
        this.displayedName = new DisplayedName(name, profanitiesChecker);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getDisplayedName() {
        return displayedName;
    }

    public Price getPrice() {
        return price;
    }

    public void changePrice(Price price) {
        this.price = price;
    }
}
