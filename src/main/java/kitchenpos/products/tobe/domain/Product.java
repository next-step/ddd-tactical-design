package kitchenpos.products.tobe.domain;

import javax.annotation.Generated;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Geonguk Han
 * @since 2020-03-07
 */
@Entity
@Table(name = "product")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    public void validatePrice() {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
