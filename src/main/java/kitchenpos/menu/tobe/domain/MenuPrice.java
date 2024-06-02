package kitchenpos.menu.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.exception.IllegalPriceException;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private Long price;

    protected MenuPrice() {
    }

    public MenuPrice(Long price) {
        validatePrice(price);
        this.price = price;
    }

    private static void validatePrice(Long price) {
        if (Objects.isNull(price) || price < 0) {
            throw new IllegalPriceException("금액은 0원 미만일 수 없습니다. ", price);
        }
    }

    public Long getPrice() {
        return price;
    }
}
