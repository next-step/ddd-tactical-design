package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Table(name = "price")
@Entity
public class Price {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Price() {
    }

    public Price(final BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal offer() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Price price1 = (Price) o;
        return price.equals(price1.price);
    }

    @Override
    public int hashCode() {
        return price.hashCode();
    }
}
