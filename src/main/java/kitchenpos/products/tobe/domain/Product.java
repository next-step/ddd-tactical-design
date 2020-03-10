package kitchenpos.products.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private ProductPrice price;

    public Product() {
    }

    public Product(final String name, final BigDecimal price) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.price = new ProductPrice(price);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void setPrice(final ProductPrice price) {
        this.price = price;
    }
}
