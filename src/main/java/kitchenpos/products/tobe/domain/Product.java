package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String displayedName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Product() {
    }

    public Product(UUID id, String displayedName, BigDecimal price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }
}
