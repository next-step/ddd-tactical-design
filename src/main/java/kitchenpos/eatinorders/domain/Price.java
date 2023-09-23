package kitchenpos.eatinorders.domain;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Price {
    private BigDecimal price;

    protected Price() {
    }

    public Price(Long price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException(String.format("가격은 0원 이상이어야 합니다. 현재 값: %s", price));
        }
        this.price = new BigDecimal(price);
    }

    public boolean isSmallerOrEqualTo(Price other) {
        return price.compareTo(other.price) <= 0;
    }

    public Price multiplyQuantity(Quantity quantity) {
        return new Price(price.longValue() * quantity.intValue());
    }

    public Price sum(Price other) {
        return new Price(price.longValue() + other.price.longValue());
    }

    public String stringValue() {
        return price.toString();
    }

    public long longValue() {
        return price.longValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
