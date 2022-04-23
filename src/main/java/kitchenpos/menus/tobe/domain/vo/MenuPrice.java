package kitchenpos.menus.tobe.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private final BigDecimal price;

    public MenuPrice(final BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴의 가격은 비어있거나, 0원 보다 작을 수 없습니다.");
        }
    }

    public boolean isGraterThan(final int menuProductsPriceSum) {
        return this.price.intValue() > menuProductsPriceSum;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuPrice that = (MenuPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }


}
