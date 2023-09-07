package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.ui.ProductRequest;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName displayedName;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    protected Product() { }

    public static Product of(ProductRequest request) {
        DisplayedName displayedName = new DisplayedName(request.getName());
        Price price = new Price(request.getPrice());
        return new Product(displayedName, price);
    }

    public Product(DisplayedName displayedName, Price price) {
        this.displayedName = displayedName;
        this.price = price;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return displayedName.value();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }
}
