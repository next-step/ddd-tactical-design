package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    private BigDecimal price;

    public Money(final BigDecimal price) {
        verifyPrice(price);
        this.price = price;
    }

    private void verifyPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(final Money money, Product product) {
        verifyPrice(money.getPrice());
        product.setPrice(money);
    }

    public BigDecimal getPrice() {
        return price;
    }
}
