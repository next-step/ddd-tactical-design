package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    @AttributeOverride(column = @Column(name = "price"), name = "value")
    private Price price;

    protected Product() {
    }

    private Product(Long id, String name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(Long id, String name, BigDecimal price) {
        this(id, name, Price.of(price));
    }

    public Product(String name, Price price) {
        this(null, name, price);
    }

    public Product(String name, BigDecimal price) {
        this(name, Price.of(price));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

}
