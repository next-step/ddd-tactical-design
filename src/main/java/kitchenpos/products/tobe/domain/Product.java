package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "displayed_name_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_to_displayed_name"))
    private DisplayedName displayedName;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "price_name_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_to_price"))
    private Price price;

    public Product() {
    }

    public Product(final String displayedName, final BigDecimal price, final Predicate<String> profanityValidator) {
        this.id = UUID.randomUUID();
        this.displayedName = new DisplayedName(displayedName, profanityValidator);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public String displayName() {
        return displayedName.display();
    }

    public BigDecimal offerPrice() {
        return price.offer();
    }

    public void changePrice(final BigDecimal price) {
        this.price = new Price(price);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
