package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {

    }

    public Product(String name, BigDecimal price) {
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    public void changePrice(BigDecimal price){
        this.price = new ProductPrice(price);
    }
}
