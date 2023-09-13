package kitchenpos.menus.domain.tobe.domain;

import static kitchenpos.products.domain.tobe.domain.Currency.*;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import kitchenpos.products.domain.tobe.domain.Currency;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private MenuPrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    protected MenuPrice() {

    }

    public static MenuPrice of(BigDecimal price) {
        return new MenuPrice(price);
    }

    public static MenuPrice of(long price) {
        return new MenuPrice(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MenuPrice))
            return false;
        MenuPrice that = (MenuPrice)o;
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

    public MenuPrice add(MenuPrice price) {
        return new MenuPrice(this.price.add(price.price));
    }

    public MenuPrice multiply(long value) {
        return new MenuPrice(this.price.multiply(BigDecimal.valueOf(value)));
    }

    public boolean isGreaterThan(MenuPrice comparePrice) {
        return price.compareTo(comparePrice.price) > 0;
    }

    public boolean isGreaterThan(BigDecimal comparePrice) {
        return price.compareTo(comparePrice) > 0;
    }

    public MenuPrice changePrice(BigDecimal newPrice) {
        return new MenuPrice(newPrice);
    }

    public MenuPrice changePrice(Long newPrice) {
        return new MenuPrice(BigDecimal.valueOf(newPrice));
    }

    public BigDecimal getValue() {
        return price;
    }
}
