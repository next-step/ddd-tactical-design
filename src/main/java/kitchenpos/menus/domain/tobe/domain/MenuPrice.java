package kitchenpos.menus.domain.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    private static final String MENU_PRICE_MUST_BE_POSITIVE_NUMBER = "메뉴 가격은 0이상의 정수 이어야 합니다. 입력 값 : %s";
    private final BigDecimal price;

    protected MenuPrice() {
        this.price = null;
    }

    protected MenuPrice(final BigDecimal price) {
        validate(price);
        this.price = price;
    }

    protected MenuPrice(final long price) {
        this(BigDecimal.valueOf(price));
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price) || isNegative(price)) {
            throw new IllegalArgumentException(String.format(MENU_PRICE_MUST_BE_POSITIVE_NUMBER, price));
        }
    }

    private boolean isNegative(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isValid(BigDecimal totalAmount) {
        return isBigger(totalAmount);
    }

    private boolean isBigger(BigDecimal price) {
        return this.price.compareTo(price) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return price.equals(menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
