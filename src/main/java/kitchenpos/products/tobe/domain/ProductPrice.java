package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.exception.WrongProductPriceException;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    private BigDecimal price;

    public ProductPrice (){}

    public ProductPrice (BigDecimal price){
        validatePrice(price);
        this.price = price;
    }

    public BigDecimal valueOf (){
        return new BigDecimal(price.toString());
    }

    public void validatePrice (final BigDecimal price){
        if(Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0){
            throw new WrongProductPriceException("상품가격을 잘못 입력했습니다.");
        }
    }

}
