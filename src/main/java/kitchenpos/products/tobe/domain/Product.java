package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Geonguk Han
 * @since 2020-03-07
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_id")
    private Long id;

    @Column(name = "name")
    private final String name;

    @Column(name = "price")
    private final Price price;

    public Product(final String name, final BigDecimal price) {
        this.name = name;
        this.price = new Price(price);
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
