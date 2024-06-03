package kitchenpos.menus.domain.tobe.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Price {

    private int price;

    private Price() {
    }

    public Price(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 한다.");
        }
        this.price = price;
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
}
