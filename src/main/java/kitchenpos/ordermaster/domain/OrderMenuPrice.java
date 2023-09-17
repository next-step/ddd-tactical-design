package kitchenpos.ordermaster.domain;

import static kitchenpos.common.Currency.*;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import kitchenpos.common.Currency;

@Embeddable
public class OrderMenuPrice {
    public static OrderMenuPrice ZERO = OrderMenuPrice.of(0L);
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private OrderMenuPrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    protected OrderMenuPrice() {

    }

    public static OrderMenuPrice of(BigDecimal price) {
        return new OrderMenuPrice(price);
    }

    public static OrderMenuPrice of(long price) {
        return new OrderMenuPrice(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof OrderMenuPrice))
            return false;
        OrderMenuPrice that = (OrderMenuPrice)o;
        return Objects.equals(price, that.price) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, currency);
    }

    private void validationOfPrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("상품의 가격은 필수로 입력해야 합니다.");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(" 상품의 가격은 0원 이상이어야 합니다.");
        }
    }

    public OrderMenuPrice add(OrderMenuPrice price) {
        return new OrderMenuPrice(this.price.add(price.price));
    }

    public boolean isGreaterThan(BigDecimal comparePrice) {
        return price.compareTo(comparePrice) > 0;
    }

    public OrderMenuPrice changePrice(BigDecimal newPrice) {
        return new OrderMenuPrice(newPrice);
    }

    public OrderMenuPrice changePrice(Long newPrice) {
        return new OrderMenuPrice(BigDecimal.valueOf(newPrice));
    }

    public OrderMenuPrice multiply(long value) {
        return new OrderMenuPrice(this.price.multiply(BigDecimal.valueOf(value)));
    }

    public BigDecimal getValue() {
        return price;
    }

}
