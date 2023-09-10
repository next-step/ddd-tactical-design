package kitchenpos.menus.tobe.domain;

import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.exception.ProductPriceException;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;


@Embeddable
public class MenuPrice {
    private BigDecimal price;

    protected MenuPrice() {

    }

    public MenuPrice(BigDecimal price) {
        if (price == null) {
            throw new ProductPriceException(ProductErrorCode.PRICE_IS_NULL);
        }
        if (isLessThanZero(price)) {
            throw new ProductPriceException(ProductErrorCode.PRICE_IS_GREATER_THAN_EQUAL_ZERO);
        }
        this.price = price;
    }

    public MenuPrice(long price) {
        this(BigDecimal.valueOf(price));
    }

    private boolean isLessThanZero(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public MenuPrice add(MenuPrice inputPrice) {
        BigDecimal add = price.add(inputPrice.price);
        return new MenuPrice(add);
    }

    public MenuPrice multiply(long input) {
        BigDecimal multiply = price.multiply(BigDecimal.valueOf(input));
        return new MenuPrice(multiply);
    }

    public BigDecimal getValue() {
        return price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice that = (MenuPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}


