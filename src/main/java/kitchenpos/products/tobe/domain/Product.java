package kitchenpos.products.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;

@Table(name = "tb_product")
@Entity(name = "tb_product")
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected Product() {
        id = UUID.randomUUID();
    }

    public Product(Name name, Price price) {
        this();
        this.name = name;
        this.price = price;
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

    public void changePrice(Price price) {
        this.price = price;
    }
}
