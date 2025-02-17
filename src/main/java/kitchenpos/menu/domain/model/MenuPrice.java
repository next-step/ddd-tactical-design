package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    private static final String PRICE_CREATION_EXCEPTION = "메뉴 가격을 채워주세요!";

    @Column(name = "price", nullable = false)
    private final BigDecimal value;

    public MenuPrice(BigDecimal value) {
        validatePrice(value);
        this.value = value;
    }

    protected MenuPrice() {
        this.value = null;
    }

    private void validatePrice(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(PRICE_CREATION_EXCEPTION);
        }
    }
}
