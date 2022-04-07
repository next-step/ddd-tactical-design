package kitchenpos.products.domain.tobe;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(String name, BigDecimal price, BanWordFilter banWordFilter) {
        this(UUID.randomUUID(), name, price, banWordFilter);
    }

    public Product(UUID id, String productName, BigDecimal price, BanWordFilter banWordFilter) {
        this.id = id;
        this.name = new ProductName(productName, banWordFilter);
        this.price = new ProductPrice(price);
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }

    public ProductPrice getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
