package kitchenpos.menus.tobe.domain;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public MenuPrice() {
    }

    public MenuPrice(BigDecimal price) {
        Assert.isTrue(!Objects.isNull(price) && price.compareTo(BigDecimal.ZERO) > 0, "메뉴의 가격은 0보다 커야합니다.");
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
