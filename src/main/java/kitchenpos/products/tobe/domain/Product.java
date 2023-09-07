package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.InvalidProductPriceException;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "displayed_name", nullable = false)
    private String displayedName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;


    public Product(UUID id, String displayedName, BigDecimal price) {
        this.id = id;
        this.displayedName = setDisplayedName(displayedName);
        this.price = setPrice(price);
    }

    private BigDecimal setPrice(BigDecimal price) {

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductPriceException();
        }

        return price;
    }

    public Product changePrice(BigDecimal price) {
        return new Product(this.id, this.displayedName, price);
    }

    private String setDisplayedName(String name) {
        return name;
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
}
