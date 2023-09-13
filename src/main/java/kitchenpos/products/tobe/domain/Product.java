package kitchenpos.products.tobe.domain;

import kitchenpos.products.ui.ProductCreateRequest;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    public Product() {
    }

    private Product(UUID id, DisplayedName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(DisplayedName name) {
        this.name = name;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public static Product create(UUID id, DisplayedName name, Price price) {
        if (name == null) {
            throw new IllegalArgumentException("이름은 필수 항목입니다.");
        }
        if (price == null) {
            throw new IllegalArgumentException("가격은 필수 항목입니다.");
        }
        return new Product(id, name, price);
    }

    public static Product create(DisplayedName name, Price price) {
        return create(UUID.randomUUID(), name, price);
    }

    public static Product of(ProductCreateRequest request) {
        return create(request.getName(), request.getPrice());
    }
}
