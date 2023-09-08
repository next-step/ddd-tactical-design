package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Product() {
    }

    private Product(UUID id, String name, BigDecimal price) {
        ProductPolicy.checkDisplayedName(name);
        ProductPolicy.checkPrice(price);

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static Product create(UUID id, String name, BigDecimal price) {
        return new Product(id, name, price);
    }

    public static Product create(String name, BigDecimal price) {
        return create(UUID.randomUUID(), name, price);
    }

    public static Product of(ProductCreateRequest request) {
        return create(request.getName(), request.getPrice());
    }
}
