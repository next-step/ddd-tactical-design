package kitchenpos.menu.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuPrice {

    public static final MenuPrice ZERO = new MenuPrice(BigDecimal.ZERO);
    public static final int MIN_PRICE_VALUE = 0;

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected MenuPrice() {
    }

    public MenuPrice(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < MIN_PRICE_VALUE) {
            throw new IllegalArgumentException("null 또는 0 미만의 값으로는 메뉴 가격을 지정할 수 없습니다.");
        }

        this.value = value;
    }

    public static MenuPrice of(BigDecimal price) {
        return new MenuPrice(price);
    }

    public MenuPrice add(MenuPrice other) {
        return new MenuPrice(this.value.add(other.value));
    }

    public boolean isLowerThan(MenuPrice other) {
        return this.value.compareTo(other.value) < 0;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuPrice)) {
            return false;
        }
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(value, menuPrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
