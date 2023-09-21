package kitchenpos.deliveryorders.domain;

import static kitchenpos.common.Currency.*;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import kitchenpos.common.Currency;

@Embeddable
public class DeliveryOrderMenuPrice {
    public static DeliveryOrderMenuPrice ZERO = DeliveryOrderMenuPrice.of(0L);
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private DeliveryOrderMenuPrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    protected DeliveryOrderMenuPrice() {

    }

    public static DeliveryOrderMenuPrice of(BigDecimal price) {
        return new DeliveryOrderMenuPrice(price);
    }

    public static DeliveryOrderMenuPrice of(long price) {
        return new DeliveryOrderMenuPrice(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DeliveryOrderMenuPrice))
            return false;
        DeliveryOrderMenuPrice that = (DeliveryOrderMenuPrice)o;
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

    public DeliveryOrderMenuPrice add(DeliveryOrderMenuPrice price) {
        return new DeliveryOrderMenuPrice(this.price.add(price.price));
    }

    public boolean isGreaterThan(BigDecimal comparePrice) {
        return price.compareTo(comparePrice) > 0;
    }

    public DeliveryOrderMenuPrice changePrice(BigDecimal newPrice) {
        return new DeliveryOrderMenuPrice(newPrice);
    }

    public DeliveryOrderMenuPrice changePrice(Long newPrice) {
        return new DeliveryOrderMenuPrice(BigDecimal.valueOf(newPrice));
    }

    public DeliveryOrderMenuPrice multiply(long value) {
        return new DeliveryOrderMenuPrice(this.price.multiply(BigDecimal.valueOf(value)));
    }

    public BigDecimal getValue() {
        return price;
    }

}
