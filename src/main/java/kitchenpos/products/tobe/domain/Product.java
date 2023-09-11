package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.products.infra.PurgomalumClient;
import org.hibernate.internal.build.AllowPrintStacktrace;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;
    @Embedded
    @Column(name = "name", nullable = false)
    private ProductName name;

    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice price;

    protected Product() {
    }
    public Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product of(ProductName name, ProductPrice price) {
        return new Product(UUID.randomUUID(), name, price);
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
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
