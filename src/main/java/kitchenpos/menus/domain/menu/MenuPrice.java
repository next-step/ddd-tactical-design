package kitchenpos.menus.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected MenuPrice() {
    }

    private MenuPrice(final BigDecimal price) {
        this.price = price;
    }

    public static MenuPrice of(final BigDecimal price) {
        validatePrice(price);
        return new MenuPrice(price);
    }

    private static void validatePrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴 가격이 입력되지 않았거나 0원 미만입니다.");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice that = (MenuPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
