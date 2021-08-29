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
    private BigDecimal _price;

    public Price() {
    }

    public Price(final BigDecimal price) {
        validate(price);
        this._price = price;
    }

    private void validate(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal offer() {
        return _price;
    }

    public void add(Price price) {
        _price = _price.add(price._price);
    }

    public void multiply(Quantity quantity) {
        _price = _price.multiply(BigDecimal.valueOf(quantity.count()));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Price price = (Price) o;
        return _price.equals(price._price);
    }

    @Override
    public int hashCode() {
        return _price.hashCode();
    }
}
