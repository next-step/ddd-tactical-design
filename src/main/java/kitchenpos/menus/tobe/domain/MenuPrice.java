package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected MenuPrice() {}

    public MenuPrice(final BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("메뉴 가격은 필수값입니다.");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴 가격은 음수가 될 수 없습니다.");
        }
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
