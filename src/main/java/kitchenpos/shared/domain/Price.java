package kitchenpos.shared.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Embeddable
public class Price implements Comparable<Price> {

    @Column(name = "price", nullable = false)
    private int price;

    protected Price() {
    }

    private Price(int price) {
        this.price = price;
    }

    public static Price of(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("상품의 가격은 0원 이상이어야 한다.");
        }
        return new Price(price);
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return price == price1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }

    @Override
    public int compareTo(@NotNull Price other) {
        return this.price - other.price;
    }

    public boolean isGreaterThan(int price) {
        return this.price - price > 0;
    }
}
