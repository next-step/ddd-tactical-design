package kitchenpos.menus.tobe.domain;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public MenuPrice(BigDecimal price) {
        if (isEmpty(price)) {
            throw new IllegalArgumentException("메뉴가격은 필수입니다");
        }
        checkPriceIsMinus(price);

        this.price = price;
    }

    private void checkPriceIsMinus(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 음수가 될수 없습니다");
        }
    }

    protected MenuPrice() {
    }

    public BigDecimal getPrice() {
        return price;
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
        return Objects.equals(price, menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
