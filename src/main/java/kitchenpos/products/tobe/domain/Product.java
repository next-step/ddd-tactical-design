package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product (){}

    public Product(String name, BigDecimal price) {
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.valueOf();
    }

    public BigDecimal getPrice() {
        return this.price.valueOf();
    }
}
