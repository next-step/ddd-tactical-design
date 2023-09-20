package kitchenpos.eatinorders.domain.tobe.domain;

import static kitchenpos.common.Currency.*;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import kitchenpos.common.Currency;

@Embeddable
public class EatInOrderMenuPrice {
    public static EatInOrderMenuPrice ZERO = EatInOrderMenuPrice.of(0L);
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private EatInOrderMenuPrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    protected EatInOrderMenuPrice() {

    }

    public static EatInOrderMenuPrice of(BigDecimal price) {
        return new EatInOrderMenuPrice(price);
    }

    public static EatInOrderMenuPrice of(long price) {
        return new EatInOrderMenuPrice(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EatInOrderMenuPrice))
            return false;
        EatInOrderMenuPrice that = (EatInOrderMenuPrice)o;
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

    public EatInOrderMenuPrice add(EatInOrderMenuPrice price) {
        return new EatInOrderMenuPrice(this.price.add(price.price));
    }

    public boolean isGreaterThan(BigDecimal comparePrice) {
        return price.compareTo(comparePrice) > 0;
    }

    public EatInOrderMenuPrice changePrice(BigDecimal newPrice) {
        return new EatInOrderMenuPrice(newPrice);
    }

    public EatInOrderMenuPrice changePrice(Long newPrice) {
        return new EatInOrderMenuPrice(BigDecimal.valueOf(newPrice));
    }

    public EatInOrderMenuPrice multiply(long value) {
        return new EatInOrderMenuPrice(this.price.multiply(BigDecimal.valueOf(value)));
    }

    public BigDecimal getValue() {
        return price;
    }

}
