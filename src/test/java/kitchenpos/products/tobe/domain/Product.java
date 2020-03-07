package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Geonguk Han
 * @since 2020-03-07
 */
public class Product {
    private final Long id;

    private final String name;

    private final BigDecimal price;

    public Product(final Long id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void validatePrice() {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
