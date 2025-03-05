package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.exception.NegativePriceException;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price implements Comparable<Price> {
    public static final Price ZERO = new Price(BigDecimal.ZERO);
    private BigDecimal price;
    protected Price() {
    }

    public Price(BigDecimal price) {
        validation(price);
        this.price = price;
    }

    private void validation(BigDecimal price){
        if(price == null || price.compareTo(new BigDecimal(0)) < 0){
            throw new NegativePriceException("양수만 입력할 수 있습니다");
        }
    }


    public BigDecimal getPrice() {
        return price;
    }

    public Price add(Price other) {
        return new Price(price.add(other.price));
    }

    public Price add(BigDecimal other) {
        return add(new Price(other));
    }

    public Price add(long other) {
        return add(new BigDecimal(other));
    }

    public Price multiply(Price other) {
        return new Price(price.multiply(other.price));
    }

    public Price multiply(BigDecimal other) {
        return multiply(new Price(other));
    }

    public Price multiply(long other) {
        return multiply(new BigDecimal(other));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Price that = (Price) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }

    @Override
    public int compareTo(@NotNull Price o) {
        return price.compareTo(o.price);
    }
}
