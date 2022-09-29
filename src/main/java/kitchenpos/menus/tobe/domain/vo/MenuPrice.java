package kitchenpos.menus.tobe.domain.vo;

import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class MenuPrice implements ValueObject {

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "price", nullable = false)
    )
    private Price value;

    protected MenuPrice() {
    }

    public MenuPrice(Price value) {
        this.value = value;
    }

    public Price getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
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
