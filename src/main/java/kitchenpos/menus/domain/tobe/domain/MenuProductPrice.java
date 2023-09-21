package kitchenpos.menus.domain.tobe.domain;

import static kitchenpos.common.Currency.*;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import kitchenpos.common.Currency;

@Embeddable
public class MenuProductPrice {
    public static MenuProductPrice ZERO = MenuProductPrice.of(0L);
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private MenuProductPrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    protected MenuProductPrice() {

    }

    public static MenuProductPrice of(BigDecimal price) {
        return new MenuProductPrice(price);
    }

    public static MenuProductPrice of(long price) {
        return new MenuProductPrice(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MenuProductPrice))
            return false;
        MenuProductPrice that = (MenuProductPrice)o;
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

    public MenuProductPrice add(MenuProductPrice price) {
        return new MenuProductPrice(this.price.add(price.price));
    }

    public MenuProductPrice multiply(long value) {
        return new MenuProductPrice(this.price.multiply(BigDecimal.valueOf(value)));
    }

    public MenuProductPrice changePrice(BigDecimal newPrice) {
        return new MenuProductPrice(newPrice);
    }

    public MenuProductPrice changePrice(Long newPrice) {
        return new MenuProductPrice(BigDecimal.valueOf(newPrice));
    }

    public BigDecimal getValue() {
        return price;
    }
}
