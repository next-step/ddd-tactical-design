package kitchenpos.menus.tobe.domain;

import java.util.Objects;

/*
  - 메뉴의 가격은 0원 이상이어야 한다.
 */
public class Price {

    private final int price;

    public Price(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("메뉴의 가격은 0원 이상이어야 한다.");
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
        return Objects.hash(price);
    }

    public boolean isExpensive(int total) {
        return price > total;
    }
}
