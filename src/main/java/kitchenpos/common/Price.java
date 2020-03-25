package kitchenpos.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    @Column(name = "price")
    private BigDecimal price;

    protected Price () {}

    public Price(BigDecimal price){
        validatePrice(price);
        this.price = price;
    }

    public BigDecimal valueOf (){
        return new BigDecimal(price.toString());
    }

    private void validatePrice(final BigDecimal price){
        if(Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0){
            throw new WrongPriceException("상품가격을 잘못 입력했습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
