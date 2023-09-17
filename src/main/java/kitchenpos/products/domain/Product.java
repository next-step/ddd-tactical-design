package kitchenpos.products.domain;

import kitchenpos.common.domain.AggregateRoot;
import kitchenpos.common.values.Name;
import kitchenpos.common.values.Price;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product implements AggregateRoot {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(Name name, Price price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void changePrice(Price price) {
        this.price = price;
        register(new ProductPriceChangeEvent(this.id));
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

}
