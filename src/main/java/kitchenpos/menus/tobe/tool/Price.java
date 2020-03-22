package kitchenpos.menus.tobe.tool;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Price {
    private BigDecimal price;

    public Price() {
    }

    public Price(BigDecimal price) {
        validPrice(price);
        this.price = price;
    }

    private void validPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal getPrice() {
        return price;
    }
}
