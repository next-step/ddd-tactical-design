package kitchenpos.menus.tobe.domain.menuproduct;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class TobeMenuProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected TobeMenuProductPrice() {
    }

    public TobeMenuProductPrice(BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("메뉴 상품의 가격은 필수로 입력 해야 합니다.");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴 상품의 가격은 0원 이상이어야 합니다. price : " + price);
        }

        this.price = price;
    }

    public static TobeMenuProductPrice multiply(final BigDecimal price, final long quantity) {
        return new TobeMenuProductPrice(price.multiply(BigDecimal.valueOf(quantity)));
    }

    public static TobeMenuProductPrice zero() {
        return new TobeMenuProductPrice(BigDecimal.ZERO);
    }

    public TobeMenuProductPrice sum(final TobeMenuProductPrice price) {
        return new TobeMenuProductPrice(this.price.add(price.getPrice()));
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TobeMenuProductPrice that = (TobeMenuProductPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
