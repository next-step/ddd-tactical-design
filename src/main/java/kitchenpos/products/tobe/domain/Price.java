package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import org.springframework.lang.NonNull;

@Embeddable
@Access(AccessType.FIELD)
public class Price {

    private BigDecimal value;

    protected Price() {
    }

    private Price(@NonNull BigDecimal value) {
        if (Objects.isNull(value) ||
            value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("invalid price");
        }

        this.value = value;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public static Price of(BigDecimal price) {
        return new Price(price);
    }

    public Price multiply(BigDecimal val) {
        return Price.of(this.value.multiply(val));
    }
}
