package kitchenpos.products.domain.tobe.domain;

import static kitchenpos.products.domain.tobe.domain.Currency.*;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Price(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    public Price() {

    }

    public static Price of(BigDecimal price) {
        return new Price(price);
    }

    public static Price of(long price) {
        return new Price(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Price))
            return false;
        Price that = (Price)o;
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

    public Price add(Price price) {
        return Price.of(this.price.add(price.price));
    }

    public boolean isGreaterThan(Price comparePrice) {
        return price.compareTo(comparePrice.price) >= 1;
    }

    public boolean isSamePrice(Price comparePrice) {
        return this.equals(comparePrice);
    }

    public Price changePrice(BigDecimal newPrice) {
        return new Price(newPrice);
    }

    public Price changePrice(Long newPrice) {
        return new Price(BigDecimal.valueOf(newPrice));
    }
}
