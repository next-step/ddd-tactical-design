package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private int price;

    private Price() {
    }

    public Price(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 한다.");
        }
        this.price = price;
    }

    int getPrice() {
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
}
