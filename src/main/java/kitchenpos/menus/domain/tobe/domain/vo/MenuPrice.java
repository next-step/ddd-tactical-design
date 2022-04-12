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
        this.price = price;
    }

    protected MenuPrice() {

    }

    public BigDecimal getValue() {
        return price;
    }
}
