package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    private BigDecimal price;

    protected Price() {
    }

    public Price(long price) {
        this(BigDecimal.valueOf(price));
    }

    public Price(Price price) {
        this(price.price);
    }

    public Price(BigDecimal price) {
        if(price == null || BigDecimal.ZERO.compareTo(price) > 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public Price add(BigDecimal price) {
        return new Price(this.price.add(price));
    }

    public Price add(Price price) {
        return price.add(this.price);
    }

    public Price multiply(long quantity) {
        return multiply(BigDecimal.valueOf(quantity));
    }

    public Price multiply(BigDecimal quantity) {
        return new Price(this.price.multiply(quantity));
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        return price != null ? price.hashCode() : 0;
    }


    public int compareTo(Price price) {
        return price.price.compareTo(this.price);
    }
}
