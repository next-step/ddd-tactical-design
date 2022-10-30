package kitchenpos.products.tobe.domain.entity;

import kitchenpos.products.tobe.domain.event.ProductPriceChangedEvent;
import kitchenpos.common.domain.vo.Name;
import kitchenpos.common.domain.vo.Price;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Product extends AbstractAggregateRoot<Product> {
    @Column(name = "id")
    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "productName", nullable = false))
    private Name name;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false))
    private Price price;

    public Product(Name name, Price price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public void changePrice(Price price) {
        this.price = price;

        registerEvent(new ProductPriceChangedEvent(id));
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    protected Product() {}
}
