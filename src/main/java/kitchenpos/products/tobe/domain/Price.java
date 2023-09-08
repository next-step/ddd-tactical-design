package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private BigDecimal price;

    public Price(BigDecimal price){
        checkPrice(price);
        this.price = price;
    }

    public void checkPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public BigDecimal getPrice(){
        return this.price;
    }



}
