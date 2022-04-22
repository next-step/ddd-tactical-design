package kitchenpos.menus.domain.tobe.domain.vo;

import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class MenuPrice extends ValueObject<MenuPrice> {

    @Column(name = "price")
    private BigDecimal price;

    public MenuPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("메뉴 가격은 0원 이상이어야 합니다.");
        }
        this.price = price;
    }

    protected MenuPrice() {

    }

    public BigDecimal getValue() {
        return price;
    }

    public boolean isGreaterThan(BigDecimal price) {
        if(this.price.compareTo(price) > 0) {
            return true;
        }
        return false;
    }
}
