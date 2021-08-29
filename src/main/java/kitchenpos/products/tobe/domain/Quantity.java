package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "quantity")
@Entity
public class Quantity {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long _quantity;

    public Quantity() {
    }

    public Quantity(final long price) {
        validate(price);
        this._quantity = price;
    }

    private void validate(final long quantity) {
        if (quantity < 0L) {
            throw new IllegalArgumentException();
        }
    }

    public long count() {
        return _quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Quantity quantity = (Quantity) o;
        return _quantity == quantity._quantity;
    }

    @Override
    public int hashCode() {
        return (int) (_quantity ^ (_quantity >>> 32));
    }
}
