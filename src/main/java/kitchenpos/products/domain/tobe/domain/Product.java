package kitchenpos.products.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Embedded
    private ProductPrice price;

    public Product() {
    }

    public Product(String name, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = new ProductPrice(price);
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }

}
