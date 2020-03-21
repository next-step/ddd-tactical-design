package kitchenpos.common;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

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
}
