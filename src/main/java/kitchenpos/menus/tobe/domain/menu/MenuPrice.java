package kitchenpos.menus.tobe.domain.menu;

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

    public MenuPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴의 가격은 0원 이상이어야 합니다. price : " + price);
        }

        this.price = price;
    }

    public MenuPrice multiply(final long quantity) {
        return new MenuPrice(price.multiply(BigDecimal.valueOf(quantity)));
    }

    public static MenuPrice zero() {
        return new MenuPrice(BigDecimal.ZERO);
    }

    public MenuPrice add(final MenuPrice price) {
        return new MenuPrice(this.price.add(price.price));
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(price, menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    public void checkSum(BigDecimal sum) {
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException(
                    "메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다. " + "price: " + price + " sum: " + sum);
        }
    }
}
