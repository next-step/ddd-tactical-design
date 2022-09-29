package kitchenpos.products.tobe.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class ProductPrice extends ValueObject {

    private Price price;

    protected ProductPrice() {
    }

    public ProductPrice(Price price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPrice price1 = (ProductPrice) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
